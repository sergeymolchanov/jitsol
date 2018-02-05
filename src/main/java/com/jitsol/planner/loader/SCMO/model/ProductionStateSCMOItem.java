package com.jitsol.planner.loader.SCMO.model;

import com.jitsol.planner.datastore.model.ProductionState;

import java.util.UUID;

public class ProductionStateSCMOItem extends ProductionState {
    private final UUID itemId;
    private final String itemName;

    private int nextVariantNum = 0;

    public ProductionStateSCMOItem(UUID itemId, String itemName) {
        super(ProductionStateType.Ready);
        this.itemId = itemId;
        this.itemName = itemName;
    }

    public UUID getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int genVariantNum() {
        nextVariantNum++;

        return nextVariantNum;
    }

    @Override
    public String getViewText() {
        return itemName;
    }
}
