package com.ittianyu.relight.todo.common.router;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import com.ittianyu.relight.todo.ResLoader;
import com.ittianyu.relight.todo.feature.MenuScreen;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.stateful.navigator.route.Route;

public class TextMenuRoute implements Route, ResLoader {
    private final String[] items;

    public TextMenuRoute(String... items) {
        this.items = items;
    }

    @Override
    public Widget build(Context context, Lifecycle lifecycle) {
        return new MenuScreen(context, lifecycle, items);
    }

    @Override
    public String path() {
        return RouterConfig.textMenu;
    }
}
