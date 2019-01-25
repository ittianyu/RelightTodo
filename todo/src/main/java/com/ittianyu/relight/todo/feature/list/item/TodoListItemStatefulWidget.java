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
import com.ittianyu.relight.todo.feature.MenuScreen;
import com.ittianyu.relight.updater.Updater;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.stateful.StatefulWidget;
import com.ittianyu.relight.widget.stateful.navigator.WidgetNavigator;
import com.ittianyu.relight.widget.stateful.state.State;

public class TodoListItemStatefulWidget extends StatefulWidget<LinearLayout, TodoListItemWidget> implements ResLoader {
    private static final int MENU_EDIT = 0;
    private static final int MENU_DELETE = 1;
    private static final int MENU_HOT_UPDATE_RESTORE = 2;
    private boolean result;
    private Runnable onDelete;
    private Action action = Action.View;

    enum Action {
        View,
        ChangeStatus,
        Delete
    }

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
                    action = Action.ChangeStatus;
                });
            })
            .onClick(v -> {
                MenuScreen.show(new Runnable1<Integer>() {
                    @Override
                    public void run(Integer item) {
                        if (null == item) {
                            return;
                        }
                        switch (item) {
                            case MENU_EDIT:
                                WidgetNavigator.push(RouterConfig.name, new AddTodoRoute(taskWithTags),
                                    new Runnable1<Boolean>() {
                                        @Override
                                        public void run(Boolean success) {
                                            if (success != null && success) {
                                                setState(() -> result = true);
                                            }
                                        }
                                    });
                                break;
                            case MENU_DELETE:
                                setStateAsync(() -> {
                                    try {
                                        result = LocalTaskDataSource.getInstance(context)
                                            .delete(taskWithTags.getTask().getId());
                                    } catch (Exception e) {
                                        result = false;
                                    }
                                    action = Action.Delete;
                                });
                                break;
                            case MENU_HOT_UPDATE_RESTORE:
                                restore();
                                break;
                        }
                    }
                },
                    getString(Strings.todo_list_menu_edit),
                    getString(Strings.todo_list_menu_delete),
                    getString(Strings.todo_list_menu_hot_update_restore)
                );

            });
        return this;
    }

    private short getNextStatus(Short status) {
        return (short) ((status + 1) % (Task.STATUS_COMPLETED + 1));
    }

    @Override
    public void update() {
        switch (action) {
            case ChangeStatus:
                if (!result) {
                    Toast.makeText(context, getString(Strings.todo_list_item_change_status_error), Toast.LENGTH_SHORT).show();
                } else {
                    super.update();
                }
                break;
            case Delete:
                if (!result) {
                    Toast.makeText(context, getString(Strings.todo_list_item_delete_error), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (onDelete != null) {
                    onDelete.run();
                }
            default:
                super.update();
                break;
        }
        action = Action.View;
    }

    public TodoListItemStatefulWidget onDelete(Runnable onDelete) {
        this.onDelete = onDelete;
        return this;
    }

    private void restore() {
        if (context instanceof Updater) {
            ((Updater) context).restore();
        }
    }
}
