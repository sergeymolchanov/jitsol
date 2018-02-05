package com.jitsol.planner.datastore;

import com.jitsol.planner.datastore.model.Order;
import com.jitsol.planner.datastore.model.ProductionRule;

public interface ILoaderDataStore {
    void init(int rulesMaxCount, int taskMaxCount);
    void setOrders(Order[] orders);
    void addRule(ProductionRule rule);
    void addRules(ProductionRule[] rules);
}
