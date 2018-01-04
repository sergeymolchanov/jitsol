package com.jitsol.planner.datastore.model;

public class ProductionRule {
    private final ProductionState stateBefore;
    private final ProductionState stateAfter;

    private final float timeBefore;
    private final float machineTime;
    private final float timeAfter;
    private final Resource mainResource;

    private final float qty;

    private final Applicability[] applicability;
    private final byte variantNum;

    public ProductionRule(ProductionState stateBefore, ProductionState stateAfter, float timeBefore, float machineTime, float timeAfter, Resource mainResource, float qty, Applicability[] applicability, byte variantNum) {
        this.stateBefore = stateBefore;
        this.stateAfter = stateAfter;
        this.timeBefore = timeBefore;
        this.machineTime = machineTime;
        this.timeAfter = timeAfter;
        this.mainResource = mainResource;
        this.qty = qty;
        this.applicability = applicability;
        this.variantNum = variantNum;
    }

    public ProductionState getStateBefore() {
        return stateBefore;
    }

    public ProductionState getStateAfter() {
        return stateAfter;
    }

    public float getTimeBefore() {
        return timeBefore;
    }

    public float getMachineTime() {
        return machineTime;
    }

    public float getTimeAfter() {
        return timeAfter;
    }

    public Resource getMainResource() {
        return mainResource;
    }

    public float getQty() {
        return qty;
    }

    public Applicability[] getApplicability() {
        return applicability;
    }

    public byte getVariantNum() {
        return variantNum;
    }
}
