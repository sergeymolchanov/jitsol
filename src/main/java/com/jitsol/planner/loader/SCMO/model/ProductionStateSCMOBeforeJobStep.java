package com.jitsol.planner.loader.SCMO.model;

import com.jitsol.planner.datastore.model.ProductionState;

import java.util.UUID;

public class ProductionStateSCMOBeforeJobStep extends ProductionState {
    private final UUID jsUUID;
    private final ProductionStateSCMOVariant variant;
    private final int number;

    public ProductionStateSCMOBeforeJobStep(UUID jsUUID, ProductionStateSCMOVariant variant, int number) {
        super(ProductionStateType.Ready);
        this.jsUUID = jsUUID;
        this.number = number;
        this.variant = variant;
    }

    @Override
    public String getViewText() {
        return String.valueOf(number);
    }

    public UUID getJsUUID() {
        return jsUUID;
    }

    public int getNumber() {
        return number;
    }

    public ProductionStateSCMOVariant getVariant() {
        return variant;
    }
}
