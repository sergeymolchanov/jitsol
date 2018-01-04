package com.jitsol.planner.web.gantt.model;

import com.fasterxml.jackson.annotation.JsonView;

public class GanttItem {

    @JsonView(Profile.PublicView.class)
    private int id;

    @JsonView(Profile.PublicView.class)
    private int parentId;

    @JsonView(Profile.PublicView.class)
    private String viewText;

    public GanttItem() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getViewText() {
        return viewText;
    }

    public void setViewText(String viewText) {
        this.viewText = viewText;
    }
}
