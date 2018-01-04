package com.jitsol.planner.web.gantt;

import com.fasterxml.jackson.annotation.JsonView;
import com.jitsol.planner.datastore.DataStore;
import com.jitsol.planner.datastore.model.*;
import com.jitsol.planner.web.gantt.model.GanttItem;
import com.jitsol.planner.web.gantt.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/gantt")
public class GanttController {

    @Autowired
    private DataStore dataStore;

    @GetMapping("/")
    public ModelAndView index() {
        Map<String, String> model = new HashMap<>();
        model.put("orderCount", String.valueOf(dataStore.getOrders().length));
        return new ModelAndView("gantt", model);
    }

    @JsonView(Profile.PublicView.class)
    @RequestMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<GanttItem> items(
            @RequestParam(value = "id", defaultValue = "0") int id,
            @RequestParam(value = "lev", defaultValue = "5") int lev) {

        List<GanttItem> items = new ArrayList<>();

        if (id == 0) {
            Order[] orders = dataStore.getOrders();

            for (Order order : orders) {
                appendTasksToGantt(items, order.getFirstLevelTask(), null, lev, -1);
            }
        } else {
            for (Task task : dataStore.getTask(id - 1).getTasksBefore()) {
                appendTasksToGantt(items, task, null, lev, -1);
            }
        }

        return items;
    }

    private void appendTasksToGantt(List<GanttItem> items, Task baseTask, GanttItem parent, int maxLevel, int levelTaskCount) {
        boolean needJoinToParent = false;

        if (baseTask.getRule() != null && baseTask.getRule().getStateAfter() != null && parent != null) {
            if (levelTaskCount == 1 && baseTask.getRule().getStateAfter().getType() == ProductionState.ProductionStateType.Intermediate) {
                needJoinToParent = true;
            } else if (baseTask.getRule().getStateAfter().getType() == ProductionState.ProductionStateType.Kitted) {
                needJoinToParent = true;
            }
        }

        GanttItem item;
        if (!needJoinToParent) {
            item = new GanttItem();
            item.setId(baseTask.getId() + 1);
            item.setParentId(parent != null ? parent.getId() : 0);
            item.setViewText(baseTask.getResultStateText() + (baseTask.getRule() != null && baseTask.getRule().getStateAfter() != null ? baseTask.getRule().getStateAfter().getType() : ""));
            items.add(item);
        } else {
            item = parent;
            item.setViewText(item.getViewText() + ", " + baseTask.getResultStateText());
        }

        if (maxLevel > 0) {
            List<Task> childTasks = baseTask.getTasksBefore();
            for (Task childTask : childTasks) {
                appendTasksToGantt(items, childTask, item, maxLevel - 1, childTasks.size());
            }
        }
    }
}
