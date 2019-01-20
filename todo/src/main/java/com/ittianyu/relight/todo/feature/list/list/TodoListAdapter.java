package com.ittianyu.relight.todo.feature.list.list;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import com.ittianyu.relight.todo.common.adapter.WidgetAdapter;
import com.ittianyu.relight.todo.common.adapter.WidgetHolder;
import com.ittianyu.relight.todo.common.datasource.entiry.TaskWithTags;
import com.ittianyu.relight.todo.feature.list.item.TodoListItemStatefulWidget;
import java.util.List;

public class TodoListAdapter extends WidgetAdapter<TaskWithTags, WidgetHolder<TodoListItemStatefulWidget>, TodoListItemStatefulWidget> {

    public TodoListAdapter(Context context, Lifecycle lifecycle, @Nullable List<TaskWithTags> data) {
        super(context, lifecycle, data);
    }

    @Override
    protected TodoListItemStatefulWidget renderItem(ViewGroup parent) {
        return new TodoListItemStatefulWidget(context, lifecycle);
    }

    @Override
    protected void convert(WidgetHolder<TodoListItemStatefulWidget> helper, TaskWithTags item) {
        int position = helper.getLayoutPosition();
        helper.widget.data(item)
            .onDelete(() -> {
                remove(position);
            });
    }
}
