package com.ittianyu.relight.todo.common.datasource.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.ittianyu.relight.todo.common.datasource.entiry.Tag;
import com.ittianyu.relight.todo.common.datasource.entiry.Task;
import com.ittianyu.relight.todo.common.datasource.local.dao.TagDao;
import com.ittianyu.relight.todo.common.datasource.local.dao.TaskDao;

@Database(entities = {Task.class, Tag.class}, version = 1, exportSchema = false)
public abstract class TodoDb extends RoomDatabase {
    private static final String DB_NAME = "todo";
    private static TodoDb instance;

    public static synchronized TodoDb getInstance(Context context) {
        if (null == instance) {
            instance = Room.databaseBuilder(context.getApplicationContext(), TodoDb.class, DB_NAME).build();
        }
        return instance;
    }

    public abstract TaskDao taskDao();
    public abstract TagDao tagDao();

}
