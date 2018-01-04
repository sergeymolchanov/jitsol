package com.jitsol.planner.loader.Tests;

import com.jitsol.planner.datastore.model.ProductionState;

public class LTProductionState extends ProductionState {

    private final int id;
    private final String name;

    public LTProductionState(int id, String name, ProductionStateType type) {
        super(type);
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getViewText() {
        return name;
    }
}
