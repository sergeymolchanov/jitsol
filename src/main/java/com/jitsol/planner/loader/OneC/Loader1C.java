package com.jitsol.planner.loader.OneC;

import com.jitsol.planner.common.ICalcSummary;
import com.jitsol.planner.common.Task;
import com.jitsol.planner.loader.ILoader;
import org.springframework.beans.factory.annotation.Value;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Loader1C implements ILoader {

    @Override
    public ICalcSummary getSummary() {
        throw new NotImplementedException();
    }

    @Override
    public void StartGetTasks() {
        throw new NotImplementedException();
    }

    @Override
    public Task[] GetTasks(int maxLen) {
        throw new NotImplementedException();
    }

    @Override
    public void EndGetTasks() {
        throw new NotImplementedException();
    }

    @Override
    public void StartSetTasks() {
        throw new NotImplementedException();
    }

    @Override
    public void SetTasks(Task[] tasks) {
        throw new NotImplementedException();
    }

    @Override
    public void EndSetTasks() {
        throw new NotImplementedException();
    }
}
