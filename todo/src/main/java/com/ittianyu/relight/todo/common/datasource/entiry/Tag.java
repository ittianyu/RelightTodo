package com.ittianyu.relight.todo.common.datasource.entiry;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tag")
public class Tag {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "task_id")
    @ForeignKey(entity = Task.class, parentColumns = "id", childColumns = "task_id", onDelete = ForeignKey.CASCADE)
    private Long taskId;
    @ColumnInfo(index = true)
    private String name;

    public Tag() {
    }

    @Ignore
    public Tag(String name) {
        this.name = name;
    }

    @Ignore
    public Tag(Long taskId, String name) {
        this.taskId = taskId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
            "id=" + id +
            ", taskId=" + taskId +
            ", name='" + name + '\'' +
            '}';
    }
}
