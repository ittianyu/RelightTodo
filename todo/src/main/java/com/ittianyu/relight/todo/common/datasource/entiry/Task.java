package com.ittianyu.relight.todo.common.datasource.entiry;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "task")
public class Task {
    public static final short STATUS_WAITING = 0;
    public static final short STATUS_DOING = 1;
    public static final short STATUS_COMPLETED = 2;

    public static final short PRIORITY_LOW = 0;
    public static final short PRIORITY_NORMAL = 1;
    public static final short PRIORITY_HIGH = 2;
    public static final short PRIORITY_URGENT = 3;

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(index = true)
    private String title;
    private String description;
    @ColumnInfo(name = "start_time", index = true)
    private Long startTime;
    @ColumnInfo(name = "end_time", index = true)
    private Long endTime;
    private Short status = STATUS_WAITING;
    private Short priority = PRIORITY_NORMAL;
    @ColumnInfo(name = "create_time", index = true)
    private Long createTime = System.currentTimeMillis();

    public Task() {
    }

    @Ignore
    public Task(String title, String description, Long startTime, Long endTime) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Ignore
    public Task(String title, String description, Long startTime, Long endTime, Short status,
        Short priority) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getPriority() {
        return priority;
    }

    public void setPriority(Short priority) {
        this.priority = priority;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", status=" + status +
            ", priority=" + priority +
            ", createTime=" + createTime +
            '}';
    }
}
