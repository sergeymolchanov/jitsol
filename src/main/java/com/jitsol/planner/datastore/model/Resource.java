package com.jitsol.planner.datastore.model;

public class Resource {
    private int id;
    private byte type;
    private float cost;

    private ResourcePlan[] resourcePlan;

    public Resource(int id, byte type, float cost, ResourcePlan[] resourcePlan) {
        this.id = id;
        this.type = type;
        this.cost = cost;
        this.resourcePlan = resourcePlan;
    }

    public byte getType() {
        return type;
    }

    public float getCost() {
        return cost;
    }

    public int getId() {
        return id;
    }

    public ResourcePlan[] getResourcePlan() {
        return resourcePlan;
    }
}
