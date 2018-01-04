package com.jitsol.planner.datastore.model;

import java.util.LinkedList;
import java.util.List;

public abstract class ProductionState {
    private final ProductionStateType type;
    private final List<ProductionRule> rulesBefore;

    public ProductionState(ProductionStateType type) {
        this.type = type;
        this.rulesBefore = new LinkedList<ProductionRule>();
    }

    public ProductionStateType getType() {
        return type;
    }

    public List<ProductionRule> getRulesBefore() {
        return rulesBefore;
    }

    public abstract String getViewText();

    public enum ProductionStateType {
        Ready,
        Kitted,
        Intermediate,
        Empty
    }
}
