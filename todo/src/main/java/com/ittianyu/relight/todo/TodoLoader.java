package com.ittianyu.relight.todo;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import com.ittianyu.dynamicupdater.loader.Loader;
import com.ittianyu.relight.todo.common.constants.Strings;
import com.ittianyu.relight.todo.common.router.RouterConfig;
import com.ittianyu.relight.todo.common.widget.CommonToolBarWidget;
import com.ittianyu.relight.todo.feature.add.AddTodoScreen;
import com.ittianyu.relight.todo.feature.list.TodoListScreen;
import com.ittianyu.relight.todo.feature.splash.SplashScreen;
import com.ittianyu.relight.widget.stateful.navigator.WidgetNavigator;
import com.ittianyu.relight.widget.stateful.navigator.route.WidgetRoute;

public class TodoLoader implements Loader, ResLoader {

    @Override
    public View render(Context context, Lifecycle lifecycle) {
        WidgetNavigator navigator = new WidgetNavigator(context, lifecycle, RouterConfig.name, true,
            new WidgetRoute(RouterConfig.splash, SplashScreen.class),
            new WidgetRoute(RouterConfig.toDoList, TodoListScreen.class),
            new WidgetRoute(RouterConfig.addTodo, CommonToolBarWidget.class, getString(Strings.add_todo_title), AddTodoScreen.class)
        );
        return navigator.render();
    }

    @Override
    public int version() {
        return 1;
    }

    @Override
    public String name() {
        return Strings.loader_name;
    }
}
