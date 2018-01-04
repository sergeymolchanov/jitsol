package com.jitsol.planner.loader.Tests;

import com.jitsol.planner.datastore.ILoaderDataStore;
import com.jitsol.planner.datastore.model.Applicability;
import com.jitsol.planner.datastore.model.Order;
import com.jitsol.planner.datastore.model.ProductionRule;
import com.jitsol.planner.datastore.model.ProductionState;
import com.jitsol.planner.loader.ILoader;

public class LoaderTests implements ILoader {

    ProductionState[] states = null;
    Order[] orders = null;
    ProductionRule[] rules = null;

    @Override
    public void prepare() {
        states = new ProductionState[]{
                new LTProductionState(0, "СБ 1'", ProductionState.ProductionStateType.Kitted),
                new LTProductionState(1, "СБ 1.1'", ProductionState.ProductionStateType.Kitted),
                new LTProductionState(2, "СБ 1.2'", ProductionState.ProductionStateType.Kitted),
                new LTProductionState(3, "СБ 1.2.1'", ProductionState.ProductionStateType.Kitted),
                new LTProductionState(4, "СБ Z'", ProductionState.ProductionStateType.Kitted),
                new LTProductionState(5, "Д 1", ProductionState.ProductionStateType.Ready),
                new LTProductionState(6, "Д 2", ProductionState.ProductionStateType.Ready),
                new LTProductionState(7, "Д 3", ProductionState.ProductionStateType.Ready),
                new LTProductionState(8, "Д 4", ProductionState.ProductionStateType.Ready),
                new LTProductionState(9, "СБ 1", ProductionState.ProductionStateType.Ready),
                new LTProductionState(10, "СБ 1.1", ProductionState.ProductionStateType.Ready),
                new LTProductionState(11, "СБ 1.2", ProductionState.ProductionStateType.Ready),
                new LTProductionState(12, "СБ 1.2.1", ProductionState.ProductionStateType.Ready),
                new LTProductionState(13, "СБ Z", ProductionState.ProductionStateType.Ready),
                new LTProductionState(14, "empty", ProductionState.ProductionStateType.Empty)
        };

        Applicability[] applicability = new Applicability[] {
                new Applicability(1, 9999)
        };

        rules = new ProductionRule[]{
                // Правила изготовления сборок
                new ProductionRule(states[0], states[9], 1, 3, 1, null, 1, applicability, (byte) 0),
                new ProductionRule(states[1], states[10], 0, 7, 2, null, 1, applicability, (byte) 0),
                new ProductionRule(states[2], states[11], 0, 2, 1, null, 1, applicability, (byte) 0),
                new ProductionRule(states[3], states[12], 0, 4, 0, null, 1, applicability, (byte) 0),
                new ProductionRule(states[4], states[13], 2, 8, 3, null, 1, applicability, (byte) 0),

                // Правила комплектации
                new ProductionRule(states[10], states[0], 0, 0, 0, null, 2, applicability, (byte) 0),
                new ProductionRule(states[11], states[0], 0, 0, 0, null, 1, applicability, (byte) 0),
                new ProductionRule(states[13], states[0], 0, 0, 0, null, 4, applicability, (byte) 0),
                new ProductionRule(states[12], states[2], 0, 0, 0, null, 3, applicability, (byte) 0),
                new ProductionRule(states[5], states[2], 0, 0, 0, null, 1, applicability, (byte) 0),
                new ProductionRule(states[6], states[2], 0, 0, 0, null, 2, applicability, (byte) 0),
                new ProductionRule(states[13], states[3], 0, 0, 0, null, 5, applicability, (byte) 0),
                new ProductionRule(states[7], states[3], 0, 0, 0, null, 10, applicability, (byte) 1),
                new ProductionRule(states[8], states[3], 0, 0, 0, null, 10, applicability, (byte) 1),
                new ProductionRule(states[5], states[4], 0, 0, 0, null, 2, applicability, (byte) 0),

                // Правила изготовления деталей
                new ProductionRule(states[14], states[5], 4, 8, 0, null, 1, applicability, (byte) 0),
                new ProductionRule(states[14], states[6], 0, 3, 7, null, 1, applicability, (byte) 0),
                new ProductionRule(states[14], states[7], 0, 5, 1, null, 1, applicability, (byte) 0),
                new ProductionRule(states[14], states[8], 0, 13, 0, null, 1, applicability, (byte) 0)
        };

        orders = new Order[] {
                new Order("Заказ 1", states[9], 1, 1, 100),
                new Order("Заказ 2", states[9], 3, 2, 150)
        };
    }

    @Override
    public void load(ILoaderDataStore dataStore) {
        dataStore.init(50, 1000);

        dataStore.setOrders(orders);
        dataStore.addRules(rules);
    }
}
