package com.jitsol.planner.datastore.model;

import java.util.LinkedList;
import java.util.List;

public class Task {
    private int id;
    private final Task taskAfter;
    private float qty;
    private final ProductionRule rule;
    private final Order order;
    private final int level;

    private final List<Task> tasksBefore = new LinkedList<Task>();

    public Task(Order order, float qty, Task taskAfter, ProductionRule rule) {
        this.order = order;
        this.qty = qty;
        this.taskAfter = taskAfter;
        this.rule = rule;
        this.level = taskAfter.level + 1;
    }

    public Task(Order order, float qty) {
        this.order = order;
        this.qty = qty;
        this.taskAfter = null;
        this.rule = null;
        this.level = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Task getTaskAfter() {
        return taskAfter;
    }

    public float getQty() {
        return qty;
    }

    public ProductionRule getRule() {
        return rule;
    }

    public Order getOrder() {
        return order;
    }

    public List<Task> getTasksBefore() {
        return tasksBefore;
    }

    public ProductionState getRequredProductionState() {
        if (rule != null) {
            return rule.getStateBefore();
        } else {
            return order.getFinalState();
        }
    }

    public String getResultStateText() {
        if (this.rule != null) {
            return this.rule.getStateAfter().getViewText();
        } else {
            return String.format("%s (%s)", order.getName(), order.getFinalState().getViewText());
        }
    }

    public int getLevel() {
        return level;
    }
}
