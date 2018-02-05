package com.jitsol.planner.datastore.model;

public class Order {
    private final String name;
    private final ProductionState finalState;
    private final float qty;
    private final int serialNumber;
    private final int requredDate;

    private Task firstLevelTask;

    public Order(String name, ProductionState finalState, float qty, int serialNumber, int requredDate) {
        this.name = name;
        this.finalState = finalState;
        this.qty = qty;
        this.serialNumber = serialNumber;
        this.requredDate = requredDate;
    }

    public String getName() {
        return name;
    }

    public ProductionState getFinalState() {
        return finalState;
    }

    public float getQty() {
        return qty;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public int getRequredDate() {
        return requredDate;
    }

    public Task getFirstLevelTask() {
        return firstLevelTask;
    }

    public void setFirstLevelTask(Task firstLevelTask) {
        this.firstLevelTask = firstLevelTask;
    }
}