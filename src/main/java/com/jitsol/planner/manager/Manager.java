package com.jitsol.planner.manager;

import com.jitsol.planner.calculator.ICalculator;
import com.jitsol.planner.loader.ILoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
@EnableScheduling
public class Manager {

    //@Autowired
    //private ICalculator calculator;

    @Autowired
    private ILoader loader;

    public void LoadData() {
        throw new NotImplementedException();
    }

    public void Calculate() {
        throw new NotImplementedException();
    }

    public void UploadData() {
        throw new NotImplementedException();
    }

    @Scheduled(fixedDelay = 1000)
    public void Test() {
        loader.Test();
    }
}