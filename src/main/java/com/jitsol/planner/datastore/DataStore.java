package com.jitsol.planner.datastore;

import com.jitsol.planner.datastore.model.Order;
import com.jitsol.planner.datastore.model.ProductionRule;
import com.jitsol.planner.datastore.model.Task;
import com.jitsol.planner.loader.ILoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value="singleton")
public class DataStore implements ILoaderDataStore, ISolverDataStore {

    @Autowired
    private ILoader loader;

    private Order[] orders = null;
    private ProductionRule[] rules = null;
    private int rulesCount = 0;
    private Task[] tasks = null;
    private int tasksCount = 0;

    public DataStore() {
    }

    @Override
    public void init(int rulesMaxCount, int taskMaxCount) {
        this.rules = new ProductionRule[rulesMaxCount];
        this.rulesCount = 0;
        this.tasks = new Task[taskMaxCount];
        this.tasksCount = 0;
    }

    @Override
    public void setOrders(Order[] orders) {
        this.orders = orders;
    }

    @Override
    public void addRule(ProductionRule rule) {
        if (this.rules == null) {
            throw new RuntimeException("Call 'init' first");
        }

        if (rulesCount >= this.rules.length) {
            throw new RuntimeException("Rules owerflow");
        }
        rules[rulesCount] = rule;
        rulesCount++;
    }

    @Override
    public void addRules(ProductionRule[] rules) {
        if (this.rules == null) {
            throw new RuntimeException("Call 'init' first");
        }

        if (rulesCount + rules.length > this.rules.length) {
            throw new RuntimeException("Rules owerflow");
        }

        System.arraycopy(rules, 0, this.rules, rulesCount, rules.length);
        rulesCount += rules.length;
    }

    @Override
    public Order[] getOrders() {
        return orders;
    }

    @Override
    public void addTask(Task task) {
        if (this.tasks == null) {
            throw new RuntimeException("Call 'init' first");
        }
        if (tasksCount >= this.tasks.length) {
            throw new RuntimeException("Tasks owerflow");
        }

        tasks[tasksCount] = task;
        task.setId(tasksCount);
        if (task.getTaskAfter() != null) {
            task.getTaskAfter().getTasksBefore().add(task);
        }
        tasksCount++;

        System.out.println(String.format("Add task %s with parent %s", task.getId(), task.getTaskAfter() == null ? "" : task.getTaskAfter().getId()));
    }

    @Override
    public Task[] getTasks() {
        return tasks;
    }

    @Override
    public Task getTask(int id) {
        return tasks[id];
    }

    @Override
    public int getTaskCount() {
        return tasksCount;
    }

    public void fillCaches() {
        for(ProductionRule rule : rules) {
            if (rule != null) {
                rule.getStateAfter().getRulesBefore().add(rule);
            }
        }
    }
}
