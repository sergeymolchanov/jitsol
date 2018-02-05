package com.jitsol.planner.loader.SCMO.model;

import com.jitsol.planner.datastore.model.*;

import java.util.UUID;

public class OrderSCMO extends Order {
    private final UUID id;

    public OrderSCMO(UUID id, String name, ProductionState finalState, float qty, int serialNumber, int requredDate) {
        super(name, finalState, qty, serialNumber, requredDate);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
