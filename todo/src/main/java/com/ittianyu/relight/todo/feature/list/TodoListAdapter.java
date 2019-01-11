package com.ittianyu.relight.todo.feature.list;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ittianyu.relight.todo.common.adapter.WidgetAdapter;
import com.ittianyu.relight.todo.common.datasource.entiry.TaskWithTags;
import java.util.List;

public class TodoListAdapter extends WidgetAdapter<TaskWithTags, BaseViewHolder> {

    public TodoListAdapter(@Nullable List<TaskWithTags> data) {
        super(null, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskWithTags item) {

    }
}
