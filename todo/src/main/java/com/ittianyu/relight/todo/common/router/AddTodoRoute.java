package com.ittianyu.relight.todo.common.router;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import com.ittianyu.relight.todo.ResLoader;
import com.ittianyu.relight.todo.common.constants.Strings;
import com.ittianyu.relight.todo.common.datasource.entiry.TaskWithTags;
import com.ittianyu.relight.todo.common.widget.CommonToolBarWidget;
import com.ittianyu.relight.todo.feature.add.AddTodoScreen;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.stateful.navigator.route.Route;

public class AddTodoRoute implements Route, ResLoader {
    private TaskWithTags data;

    public AddTodoRoute(TaskWithTags data) {
        this.data = data;
    }

    @Override
    public Widget build(Context context, Lifecycle lifecycle) {
        return new CommonToolBarWidget(context, lifecycle, getString(Strings.add_todo_title), AddTodoScreen.class, data);
    }

    @Override
    public String path() {
        return RouterConfig.toDoEditor;
    }
}
