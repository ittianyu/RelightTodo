package com.ittianyu.relight.todo.common.datasource.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.ittianyu.relight.todo.common.datasource.entiry.Tag;
import java.util.List;

@Dao
public interface TagDao {

    @Insert
    Long[] add(List<Tag> tag);

    @Delete
    int delete(List<Tag> tags);

    @Update
    int update(List<Tag> tags);

    @Query("select * from tag where task_id = :taskId")
    List<Tag> query(long taskId);
}
