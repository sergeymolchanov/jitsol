package com.jitsol.planner.solver.LocalSolver;

import com.jitsol.planner.datastore.ISolverDataStore;
import com.jitsol.planner.datastore.model.ProductionRule;
import com.jitsol.planner.datastore.model.ProductionState;
import com.jitsol.planner.datastore.model.Task;
import com.jitsol.planner.solver.ISolver;
import com.jitsol.planner.solver.exception.SolverException;
import com.jitsol.planner.datastore.model.Order;

import java.util.*;

public class LocalSolver implements ISolver {

    @Override
    public void Solve(ISolverDataStore dataStore) throws SolverException {
        for(Order order : dataStore.getOrders()) {
            if (order.getFirstLevelTask() == null) {
                Task firstTask = new Task(order, order.getQty());
                order.setFirstLevelTask(firstTask);
                dataStore.addTask(firstTask);
            }
        }

        for(int z=0; z<2; z++) {
            int processedTaskCount = 0;
            int taskCount = 0;
            while ((taskCount = dataStore.getTaskCount()) != processedTaskCount) {
                Task[] tasks = dataStore.getTasks();

                for (int i = processedTaskCount; i < taskCount; i++) {
                    System.out.println("Task " + i);
                    processTask(dataStore, tasks[i]);
                }
                processedTaskCount = taskCount;
            }
        }
    }

    private void processTask(ISolverDataStore dataStore, Task task) {
        ProductionState requredState = task.getRequredProductionState();

        int usedVariantNum = getUsedVariantNum(requredState);

        List<ProductionRule> usedRules = new ArrayList();

        for(ProductionRule rule : requredState.getRulesBefore()) {
            if (rule.getVariantNum() == usedVariantNum) {
                usedRules.add(rule);
            }
        }
        useRulesForTask(dataStore, task, usedRules);
    }

    private void useRulesForTask(ISolverDataStore dataStore, Task task, List<ProductionRule> rules) {

        Map<ProductionRule, ProductionRuleCmpObject> ruleMap = new HashMap();
        List<Task> oldTasksBefore = task.getTasksBefore();

        for(ProductionRule rule : rules) {
            Task newTask = new Task(task.getOrder(), task.getQty() * rule.getQty(), task, rule);
            dataStore.addTask(newTask);

            if (!ruleMap.containsKey(rule)) {
                ruleMap.put(rule, new ProductionRuleCmpObject());
            }
            ruleMap.get(rule).setNeeded(true);
        }

    }

    private int getUsedVariantNum(ProductionState state) {
        return 0;
    }

    private class ProductionRuleCmpObject {
        private boolean isNeeded;

        public boolean isNeeded() {
            return isNeeded;
        }

        public void setNeeded(boolean needed) {
            isNeeded = needed;
        }
    }
}