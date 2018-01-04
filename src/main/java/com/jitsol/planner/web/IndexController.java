package com.jitsol.planner.web;

import com.jitsol.planner.datastore.DataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    DataStore dataStore;

    @GetMapping("/")
    public ModelAndView index() {
        Map<String, String> model = new HashMap<>();
        model.put("name", "Sergey " + dataStore.getTaskCount());
        return new ModelAndView("index", model);
    }
}