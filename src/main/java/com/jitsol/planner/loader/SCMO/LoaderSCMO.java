package com.jitsol.planner.loader.SCMO;

import com.jitsol.planner.datastore.ILoaderDataStore;
import com.jitsol.planner.datastore.model.Order;
import com.jitsol.planner.loader.ILoader;
import com.jitsol.planner.loader.LoaderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class LoaderSCMO implements ILoader {
    private final Map<UUID, ProductionStateSCMOItemReady> itemReadyStates = new HashMap<>();
    private final Map<UUID, OrderSCMO> orderList = new HashMap<>();

    @Autowired
    @Qualifier("jdbcLoaderSCMO")
    JdbcTemplate jdbcTemplate;

    @Override
    public void prepare() {
    }

    @Override
    public void load(ILoaderDataStore dataStore) throws LoaderException {

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(getQueryByName("OrderList"));
        for (Map<String, Object> row : rows) {
            UUID itemUUID = UUID.fromString((String)row.get("iptr"));
            UUID orderUUID = UUID.fromString((String)row.get("rowpointer"));

            ProductionStateSCMOItemReady itemReadyState = itemReadyStates.get(itemUUID);
            if (itemReadyState == null) {
                itemReadyState = new ProductionStateSCMOItemReady(itemUUID);
                itemReadyStates.put(itemUUID, itemReadyState);
            }

            OrderSCMO order = new OrderSCMO(orderUUID,
                    (String)row.get("order_name"),
                    itemReadyState,
                    ((java.math.BigDecimal)row.get("qty")).floatValue(),
                    Integer.valueOf((String)row.get("serial_number")),
                    0// TODO: convert to int row.get("demanddate")
                    );
            orderList.put(orderUUID, order);
        }

        dataStore.init(500, 500);
        dataStore.setOrders(orderList.values().toArray(new Order[orderList.size()]));
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