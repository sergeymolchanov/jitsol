package com.jitsol.planner.loader.SCMO;

import com.jitsol.planner.common.ICalcSummary;
import com.jitsol.planner.common.Task;
import com.jitsol.planner.loader.ILoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class LoaderSCMO implements ILoader {

    @Autowired
    @Qualifier("jdbcLoaderSCMO")
    JdbcTemplate jdbcTemplate;

    @Override
    public ICalcSummary getSummary() {
        throw new NotImplementedException();
    }

    @Override
    public void StartGetTasks() {
        int countOfStock = this.jdbcTemplate.queryForObject(
                "select count(*) from itemstock", Integer.class);

        System.out.println("SCMO: " + countOfStock);
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