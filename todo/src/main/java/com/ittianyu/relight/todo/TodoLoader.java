package com.ittianyu.relight.todo;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import com.ittianyu.relight.loader.Loader;
import com.ittianyu.relight.todo.common.router.RouterConfig;
import com.ittianyu.relight.todo.feature.list.TodoListScreen;
import com.ittianyu.relight.todo.feature.splash.SplashScreen;
import com.ittianyu.relight.widget.stateful.navigator.WidgetNavigator;
import com.ittianyu.relight.widget.stateful.navigator.route.WidgetRoute;

public class TodoLoader implements Loader {

    @Override
    public View render(Context context, Lifecycle lifecycle) {
        WidgetNavigator navigator = new WidgetNavigator(context, lifecycle, RouterConfig.name, true,
            new WidgetRoute(RouterConfig.splash, SplashScreen.class),
            new WidgetRoute(RouterConfig.toDoList, TodoListScreen.class)
        );
        return navigator.render();
    }
}
