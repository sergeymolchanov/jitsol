package com.jitsol.planner.loader.SCMO.model;

import com.jitsol.planner.datastore.model.ProductionState;

import java.util.UUID;

public class ProductionStateSCMOVariant extends ProductionState {
    private final UUID variantId;
    private final ProductionStateSCMOItem item;
    private final String name;

    public ProductionStateSCMOVariant(UUID variantId, ProductionStateSCMOItem item, String name) {
        super(ProductionStateType.Ready);
        this.variantId = variantId;
        this.item = item;
        this.name = name;
    }

    public UUID getVariantId() {
        return variantId;
    }

    public String getVariantName() {
        return name;
    }

    @Override
    public String getViewText() {
        return name;
    }

    public ProductionStateSCMOItem getItem() {
        return item;
    }
}
