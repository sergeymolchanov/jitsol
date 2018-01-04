package com.jitsol.planner.loader.SCMO;

import com.jitsol.planner.datastore.model.ProductionState;

import java.util.UUID;

public class ProductionStateSCMOItemReady extends ProductionState {
    private final UUID itemId;

    private String itemName;

    public ProductionStateSCMOItemReady(UUID itemId) {
        super(ProductionStateType.Ready);
        this.itemId = itemId;
    }

    public UUID getItemId() {
        return itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    @Override
    public String getViewText() {
        return itemName;
    }
}
