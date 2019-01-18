package com.ittianyu.relight.todo.common.datasource.local;

import android.content.Context;
import android.text.TextUtils;
import com.ittianyu.relight.todo.common.datasource.TaskDataSource;
import com.ittianyu.relight.todo.common.datasource.entiry.SortEntry;
import com.ittianyu.relight.todo.common.datasource.entiry.Tag;
import com.ittianyu.relight.todo.common.datasource.entiry.Task;
import com.ittianyu.relight.todo.common.datasource.entiry.TaskWithTags;
import com.ittianyu.relight.todo.common.datasource.local.dao.TagDao;
import com.ittianyu.relight.todo.common.datasource.local.dao.TaskDao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocalTaskDataSource implements TaskDataSource {
    public static final int COUNT_PER_PAGE = 20;
    private static LocalTaskDataSource instance;

    public static synchronized LocalTaskDataSource getInstance(Context context) {
        if (null == instance) {
            instance = new LocalTaskDataSource(context);
        }
        return instance;
    }

    private final TodoDb db;
    private final TaskDao taskDao;
    private final TagDao tagDao;

    public LocalTaskDataSource(Context context) {
        db = TodoDb.getInstance(context);
        taskDao = db.taskDao();
        tagDao = db.tagDao();
    }

    public LocalTaskDataSource(TodoDb db) {
        this.db = db;
        taskDao = db.taskDao();
        tagDao = db.tagDao();
    }

    private boolean filterTask(Task task) {
        if (TextUtils.isEmpty(task.getTitle())) {
            return false;
        }
        if (task.getStartTime() > task.getEndTime()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean add(Task task, List<Tag> tags) {
        if (!filterTask(task)) {
            return false;
        }

        db.beginTransaction();
        Long taskId = taskDao.add(task);
        if (taskId <= 0) {
            db.endTransaction();
            return false;
        }
        task.setId(taskId);

        if (null == tags) {
            tags = Collections.emptyList();
        }
        // set task id
        for (Tag tag: tags) {
            tag.setTaskId(task.getId());
        }

        Long[] ids = tagDao.add(tags);
        for (Long id : ids) {
            if (id <= 0) {
                db.endTransaction();
                return false;
            }
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        return true;
    }

    @Override
    public boolean delete(Long taskId) {
        Task task = new Task();
        task.setId(taskId);
        int count = taskDao.delete(task);
        return count > 0;
    }

    @Override
    public boolean update(Task task, List<Tag> tags) {
        if (!filterTask(task)) {
            return false;
        }

        db.beginTransaction();

        int count = taskDao.update(task);
        if (count <= 0) {
            db.endTransaction();
            return false;
        }

        if (null == tags) {
            tags = Collections.emptyList();
        }
        tagDao.update(tags);

        db.setTransactionSuccessful();
        db.endTransaction();
        return true;
    }

    @Override
    public List<TaskWithTags> query(int page, long startTime, long endTime) {
        int offset = (page - 1) * COUNT_PER_PAGE;
        List<Task> tasks = taskDao.query(offset, COUNT_PER_PAGE, startTime, endTime);
        List<TaskWithTags> result = new ArrayList<>();
        for (Task task : tasks) {
            List<Tag> tags = tagDao.query(task.getId());
            result.add(new TaskWithTags(task, tags));
        }
        return result;
    }

    private void generateOrderBy(List<SortEntry> sortEntries) {
        StringBuilder orderBy = new StringBuilder();
        if (sortEntries == null) {
            sortEntries = Collections.emptyList();
        }
        for (SortEntry sortEntry : sortEntries) {
            String field = sortEntry.getField();
            field = field.replaceAll(" ", "");
            orderBy.append(field);
            orderBy.append(" ");
            orderBy.append(sortEntry.getOrder() == SortEntry.ORDER_ASC ? "asc" : "desc");
            orderBy.append(",");
        }
        if (orderBy.length() > 0) {
            orderBy.deleteCharAt(orderBy.length() - 1);
        }
    }
}
