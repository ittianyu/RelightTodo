package com.ittianyu.relight.clickcount;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import com.ittianyu.relight.clickcount.constants.Strings;
import com.ittianyu.relight.loader.Loader;

public class ClickCountLoader implements Loader {

    @Override
    public View render(Context context, Lifecycle lifecycle) {
        return new ClickCountWidget(context, lifecycle).render();
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
