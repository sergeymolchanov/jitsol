package com.jitsol.planner.datastore.model;

public class ResourcePlan {
    private int globalPeriodId;
    private int capacity;
    private int usedQty;

    public ResourcePlan(int globalPeriodId, int capacity) {
        this.globalPeriodId = globalPeriodId;
        this.capacity = capacity;
        this.usedQty = 0;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getUsedQty() {
        return usedQty;
    }

    public int getGlobalPeriodId() {
        return globalPeriodId;
    }

    public int getDeficit() {
        return capacity - usedQty;
    }

    public void Use(int capacity) {
        this.capacity += capacity;
    }

    public void UnUse(int capacity) {
        this.capacity -= capacity;
    }
}
