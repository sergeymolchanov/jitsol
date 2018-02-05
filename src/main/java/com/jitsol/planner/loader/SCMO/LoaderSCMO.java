package com.jitsol.planner.loader.SCMO;

import com.jitsol.planner.datastore.ILoaderDataStore;
import com.jitsol.planner.datastore.model.Applicability;
import com.jitsol.planner.datastore.model.Order;
import com.jitsol.planner.datastore.model.ProductionRule;
import com.jitsol.planner.datastore.model.ProductionState;
import com.jitsol.planner.loader.ILoader;
import com.jitsol.planner.loader.LoaderException;
import com.jitsol.planner.loader.SCMO.model.OrderSCMO;
import com.jitsol.planner.loader.SCMO.model.ProductionStateSCMOItem;
import com.jitsol.planner.loader.SCMO.model.ProductionStateSCMOBeforeJobStep;
import com.jitsol.planner.loader.SCMO.model.ProductionStateSCMOVariant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class LoaderSCMO implements ILoader {
    private static final int SERIAL_NUMBER_DIGITS = 5;
    private static final Applicability[] applicabilityAny = new Applicability[] { new Applicability(Integer.MIN_VALUE, Integer.MAX_VALUE) };

    private final Map<UUID, ProductionStateSCMOItem> items = new HashMap<>();
    private final Map<UUID, ProductionStateSCMOVariant> variants = new HashMap<>();
    private final Map<UUID, ProductionStateSCMOBeforeJobStep> jobSteps = new HashMap<>();

    private final Map<UUID, OrderSCMO> orderList = new HashMap<>();

    @Autowired
    @Qualifier("jdbcLoaderSCMO")
    JdbcTemplate jdbcTemplate;

    @Override
    public void prepare() {
    }

    @Override
    public void load(ILoaderDataStore dataStore) throws LoaderException {

        dataStore.init(5000000, 5000000);

        {   // Load Items
            System.out.println("Load items");
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(getQueryByName("ItemList"));
            for (Map<String, Object> row : rows) {
                UUID itemUUID = UUID.fromString((String) row.get("rowpointer"));
                String name = (String) row.get("name");

                ProductionStateSCMOItem item = new ProductionStateSCMOItem(itemUUID, name);
                items.put(itemUUID, item);
            }
            System.out.println("Loaded " + items.size() + " items");
        }

        {   // Load Orders
            System.out.println("Load Orders");
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(getQueryByName("OrderList"));
            for (Map<String, Object> row : rows) {
                UUID itemUUID = UUID.fromString((String) row.get("iptr"));
                UUID orderUUID = UUID.fromString((String) row.get("rowpointer"));

                ProductionStateSCMOItem item = items.get(itemUUID);
                if (item == null) {
                    throw new LoaderException("Item not found: " + itemUUID.toString());
                }

                OrderSCMO order = new OrderSCMO(orderUUID,
                        (String) row.get("order_name"),
                        item,
                        ((java.math.BigDecimal) row.get("qty")).floatValue(),
                        Integer.valueOf((String) row.get("serial_number")),
                        0// TODO: convert to int row.get("demanddate")
                );
                orderList.put(orderUUID, order);
            }
            System.out.println("Loaded " + orderList.size() + " Orders");
        }

        dataStore.setOrders(orderList.values().toArray(new Order[orderList.size()]));

        {   // Load variants
            System.out.println("Load variants");
            ProductionStateSCMOItem curItem = null; // Outside for fast
            List<Map<String, Object>>  rows = jdbcTemplate.queryForList(getQueryByName("VariantList"));
            for (Map<String, Object> row : rows) {
                UUID itemUUID = UUID.fromString((String) row.get("iptr"));
                UUID variantUUID = UUID.fromString((String) row.get("vptr"));

                if (curItem == null || !itemUUID.equals(curItem.getItemId())) {
                    curItem = items.get(itemUUID);
                }

                if (curItem == null) {
                    throw new LoaderException("Item not found: " + itemUUID.toString());
                }

                ProductionStateSCMOVariant variant = new ProductionStateSCMOVariant(variantUUID, curItem, (String) row.get("name"));
                variants.put(variantUUID, variant);
            }
            System.out.println("Loaded " + variants.size() + " variants");
        }

        {   // Load variant ecn, add rules
            System.out.println("Load variant ecn, add rules");
            int ecnCount = 0;
            int serialNumberMul = (int)Math.pow(10, SERIAL_NUMBER_DIGITS);
            ProductionStateSCMOVariant curVariant = null;
            List<Map<String, Object>>  rows = jdbcTemplate.queryForList(getQueryByName("VariantEcn"));
            List<Applicability> applicabilityList = new ArrayList<>();
            for (Map<String, Object> row : rows) {
                UUID variantUUID = UUID.fromString((String) row.get("vptr"));
                int from;
                int to;

                try {
                    int numberFrom = Integer.valueOf ((String) row.get("from_serial_number"));
                    int numberTo = Integer.valueOf ((String) row.get("to_serial_number"));
                    int numberPrefix = (int)row.get("prefix");

                    if (numberFrom >= serialNumberMul || numberTo >= serialNumberMul) {
                        throw new LoaderException("Max len for serial_number is " + SERIAL_NUMBER_DIGITS);
                    }
                    from = numberPrefix * serialNumberMul + numberFrom;
                    to = numberPrefix * serialNumberMul + numberTo;
                } catch (NumberFormatException e) {
                    //throw new LoaderException("Serial number must be numeric");
                    System.out.println("Serial number must be numeric");
                    continue;
                }

                if (curVariant == null || !variantUUID.equals(curVariant.getVariantId())) {
                    if (curVariant != null) {
                        // Все применимости на данный вариант собраны, можно добавлять правило

                        ProductionRule rule = new ProductionRule(
                                curVariant,
                                curVariant.getItem(),
                                0,
                                0,
                                0,
                                null,
                                1,
                                applicabilityList.toArray(new Applicability[applicabilityList.size()]),
                                (byte)curVariant.getItem().genVariantNum());
                        dataStore.addRule(rule);
                        ecnCount+= applicabilityList.size();
                        applicabilityList.clear();
                    }

                    curVariant = variants.get(variantUUID);

                    if (curVariant == null) {
                        throw new LoaderException("Variant not found: " + variantUUID.toString());
                    }
                }

                applicabilityList.add(new Applicability(from, to));
            }
            if (curVariant != null) {
                // Добавляем последнюю применимость после выхода из цикла
                ProductionRule rule = new ProductionRule(
                        curVariant,
                        curVariant.getItem(),
                        0,
                        0,
                        0,
                        null,
                        1,
                        applicabilityList.toArray(new Applicability[applicabilityList.size()]),
                        (byte)curVariant.getItem().genVariantNum());
                dataStore.addRule(rule);
                ecnCount+= applicabilityList.size();
                applicabilityList.clear();
            }
            System.out.println("Loaded " + ecnCount + " variant ecn");
        }

        {
            // Load jobsteps
            System.out.println("Load jobsteps");
            List<Map<String, Object>>  rows = jdbcTemplate.queryForList(getQueryByName("JobStepList"));
            ProductionStateSCMOBeforeJobStep prevBeforeJobStep = null;

            for (Map<String, Object> row : rows) {
                UUID jsUUID = UUID.fromString((String) row.get("rowpointer"));
                UUID variantUUID = UUID.fromString((String) row.get("vptr"));

                try {
                    int number = (Integer)row.get("number");

                    ProductionStateSCMOBeforeJobStep beforeJobStep;
                    ProductionRule rule;
                    if (prevBeforeJobStep == null || !prevBeforeJobStep.getVariant().getVariantId().equals(variantUUID)) {
                        // Первый ЦЗ в этом варианте
                        ProductionStateSCMOVariant variant = variants.get(variantUUID);
                        beforeJobStep = new ProductionStateSCMOBeforeJobStep(jsUUID, variant, number);

                        rule = new ProductionRule(beforeJobStep, variant, 0, 0, 0, null, 1, null, (byte)1);
                    } else {
                        // Второй и далее ЦЗ

                        beforeJobStep = new ProductionStateSCMOBeforeJobStep(jsUUID, prevBeforeJobStep.getVariant(), number);
                        rule = new ProductionRule(beforeJobStep, prevBeforeJobStep, 0, 0, 0, null, 1, null, (byte)1);
                    }
                    jobSteps.put(jsUUID, beforeJobStep);
                    dataStore.addRule(rule);

                    prevBeforeJobStep = beforeJobStep;
                } catch (NumberFormatException e) {
                    System.out.println("number must be numeric");
                }
            }
            System.out.println("Loaded " + jobSteps.size() + " jobSteps");
        }
    }

    private String getQueryByName(String name) throws LoaderException {
        try {
            return new String(Files.readAllBytes(Paths.get("resources","sql", name + ".sql")));
        } catch (IOException e) {
            throw new LoaderException(e);
        }
    }

    /*
    private final String SQL_SELECT_ITEMSTOCK = "select i.rowpointer, i.iptr, i.allocatable_qty from Vitemstock i where i.allocatable_qty > 0";

    @Override
    public ICalcSummary getSummary() {
        throw new NotImplementedException();
    }

    @Override
    public void StartGetTasks() {
        int countOfStock = this.jdbcTemplate.queryForObject(
                "select count(*) from itemstock", Integer.class);

        System.out.println("SCMO: " + countOfStock);
    }*/
}