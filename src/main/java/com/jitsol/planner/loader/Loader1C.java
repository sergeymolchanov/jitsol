package com.jitsol.planner.loader;

import com.jitsol.planner.common.ICalcSummary;
import org.springframework.beans.factory.annotation.Value;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Loader1C implements ILoader {

    @Value("${loader.1C.dbURL}")
    private String dbURL;

    @Override
    public ICalcSummary getSummary() {
        throw new NotImplementedException();
    }

    @Override
    public void Test() {
        System.out.println("1C");
    }
}
