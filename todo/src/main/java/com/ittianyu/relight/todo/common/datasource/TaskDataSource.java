package com.ittianyu.relight.todo.common.datasource;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import com.ittianyu.relight.todo.common.datasource.entiry.SortEntry;
import com.ittianyu.relight.todo.common.datasource.entiry.Tag;
import com.ittianyu.relight.todo.common.datasource.entiry.Task;
import com.ittianyu.relight.todo.common.datasource.entiry.TaskWithTags;
import java.util.List;

public interface TaskDataSource {
    boolean add(Task task, List<Tag> tags);
    boolean delete(Long taskId);
    boolean update(Task task, List<Tag> tags);

    /**
     *
     * @param page start from 1
     * @param startTime
     * @param endTime
     * @param sortEntries can be null
     * @return
     */
    List<TaskWithTags> query(int page, long startTime, long endTime, List<SortEntry> sortEntries);
}
