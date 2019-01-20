package com.ittianyu.relight.todo.common.widget;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.v4.view.QuickScrollViewPager;
import android.support.v4.view.ViewPager;
import com.ittianyu.relight.widget.native_.WidgetPager;

public class QuickScrollWidgetPager extends WidgetPager {

    public QuickScrollWidgetPager(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    public ViewPager createView(Context context) {
        return new QuickScrollViewPager(context);
    }
}
