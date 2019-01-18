package com.ittianyu.relight.todo.feature.list.item;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.ittianyu.relight.thread.Runnable1;
import com.ittianyu.relight.todo.ResLoader;
import com.ittianyu.relight.todo.common.constants.Strings;
import com.ittianyu.relight.todo.common.datasource.entiry.Task;
import com.ittianyu.relight.todo.common.datasource.entiry.TaskWithTags;
import com.ittianyu.relight.todo.common.datasource.local.LocalTaskDataSource;
import com.ittianyu.relight.todo.common.router.AddTodoRoute;
import com.ittianyu.relight.todo.common.router.RouterConfig;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.stateful.StatefulWidget;
import com.ittianyu.relight.widget.stateful.navigator.WidgetNavigator;
import com.ittianyu.relight.widget.stateful.state.State;

public class TodoListItemStatefulWidget extends StatefulWidget<LinearLayout, TodoListItemWidget> implements ResLoader {

    private boolean result;

    public TodoListItemStatefulWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected State<TodoListItemWidget> createState(Context context) {
        return StateUtils.create(new TodoListItemWidget(context, lifecycle));
    }

    public TodoListItemStatefulWidget data(TaskWithTags taskWithTags) {
        widget
            .data(taskWithTags)
            .onCheck(v -> {
                Task task = taskWithTags.getTask();
                setStateAsync(() -> {
                    Short currentStatus = task.getStatus();
                    task.setStatus(getNextStatus(currentStatus));
                    try {
                        result = LocalTaskDataSource.getInstance(context)
                            .update(task, taskWithTags.getTags());
                    } catch (Exception e) {
                        result = false;
                    }
                    if (!result) {
                        task.setStatus(currentStatus);
                    }
                });
            })
            .onClick(v -> {
                WidgetNavigator.push(RouterConfig.name, new AddTodoRoute(taskWithTags),
                    new Runnable1<Boolean>() {
                        @Override
                        public void run(Boolean success) {
                            if (success != null && success) {
                                setState(() -> result = true);
                            }
                        }
                    });
            });
        return this;
    }

    private short getNextStatus(Short status) {
        return (short) ((status + 1) % (Task.STATUS_COMPLETED + 1));
    }

    @Override
    public void update() {
        if (!result) {
            Toast.makeText(context, getString(Strings.todo_list_item_change_status_error), Toast.LENGTH_SHORT).show();
        } else {
            super.update();
        }
    }
}
