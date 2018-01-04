package com.jitsol.planner.datastore.model;

public class Applicability {
    private final int from;
    private final int to;

    public Applicability(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}
