package com.ittianyu.relight.todo.common.datasource.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.ittianyu.relight.todo.common.datasource.TaskDataSource;
import com.ittianyu.relight.todo.common.datasource.entiry.Tag;
import com.ittianyu.relight.todo.common.datasource.entiry.Task;
import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    Long add(Task task);

    @Delete
    int delete(Task task);

    @Update
    int update(Task task);

    @Query("select * from task where not (start_time > :endTime or end_time < :startTime) order by :orderBy limit :limit offset :offset")
    List<Task> query(int offset, int limit, long startTime, long endTime, String orderBy);
}

//   ------
//  ----

//  ------
//   --

//  -----
//    ------

//    ----
// --

//  ---
//      --
