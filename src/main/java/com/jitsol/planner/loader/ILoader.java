package com.jitsol.planner.loader;

import com.jitsol.planner.common.ICalcSummary;
import com.jitsol.planner.common.Task;

public interface ILoader {
    ICalcSummary getSummary();

    void StartGetTasks();
    Task[] GetTasks(int maxLen);
    void EndGetTasks();

    void StartSetTasks();
    void SetTasks(Task[] tasks);
    void EndSetTasks();
}