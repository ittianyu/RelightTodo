package com.ittianyu.relight.todo.main;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.ittianyu.relight.todo.common.datasource.entiry.Tag;
import com.ittianyu.relight.todo.common.datasource.entiry.Task;
import com.ittianyu.relight.todo.common.datasource.entiry.TaskWithTags;
import com.ittianyu.relight.todo.common.datasource.local.LocalTaskDataSource;
import com.ittianyu.relight.todo.common.datasource.local.TodoDb;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final long DAY_MS = 1000L * 60 * 60 * 24;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.ittianyu.relight.todo.main.test", appContext.getPackageName());
    }

    TodoDb todoDb;
    LocalTaskDataSource ds;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        todoDb = Room.inMemoryDatabaseBuilder(context, TodoDb.class).build();
        ds = new LocalTaskDataSource(todoDb);
    }

    @After
    public void closeDb() throws IOException {
        if (null != todoDb) {
            todoDb.close();
        }
    }

    @Test
    public void add() {
        Task task = new Task();
        task.setTitle("task1");
        task.setDescription("desc1");
        task.setStartTime(System.currentTimeMillis());
        task.setEndTime(System.currentTimeMillis() + DAY_MS * 2);

        List<Tag> tags = Arrays.asList(
            new Tag("tag1"),
            new Tag("tag2"),
            new Tag("tag3")
        );

        boolean result = ds.add(task, tags);

        System.out.println("add:" + result);
    }

    @Test
    public void delete() {
        boolean result = ds.delete(1L);
        System.out.println("delete:" + result);
    }

    @Test
    public void update() {
        Task task = new Task();
        task.setId(1L);
        boolean result = ds.update(task, null);
        System.out.println("update:" + result);
    }

    @Test
    public void query() {
        List<TaskWithTags> result = ds
            .query(1, System.currentTimeMillis(), System.currentTimeMillis() + DAY_MS, null);
        System.out.println("query:" + result);
    }

    @Test
    public void test() {
        add();
        query();
        update();
        query();
        delete();
        query();
        add();
        add();
        query();
    }
}
