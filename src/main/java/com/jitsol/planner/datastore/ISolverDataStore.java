package com.jitsol.planner.datastore;

import com.jitsol.planner.datastore.model.Order;
import com.jitsol.planner.datastore.model.Task;

public interface ISolverDataStore {
    Order[] getOrders();

    Task[] getTasks();
    Task getTask(int id);
    int getTaskCount();
    void addTask(Task task);
}
