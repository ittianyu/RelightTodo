package com.ittianyu.relight.todo.common.datasource.entiry;

import java.util.List;

public class TaskWithTags {
    private Task task;
    private List<Tag> tags;

    public TaskWithTags() {
    }

    public TaskWithTags(Task task, List<Tag> tags) {
        this.task = task;
        this.tags = tags;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "TaskWithTags{" +
            "task=" + task +
            ", tags=" + tags +
            '}';
    }
}
