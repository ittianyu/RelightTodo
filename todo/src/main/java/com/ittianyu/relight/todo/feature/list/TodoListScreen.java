package com.ittianyu.relight.todo.feature.list;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.v4.view.ViewPager;
import com.ittianyu.relight.todo.ResLoader;
import com.ittianyu.relight.todo.common.callback.OnClick1;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.native_.WidgetPager;
import com.ittianyu.relight.widget.stateful.StatefulWidget;
import com.ittianyu.relight.widget.stateful.state.State;

public class TodoListScreen extends StatefulWidget<ViewPager, WidgetPager> implements ResLoader {
    private OnClick1<Boolean> onClickChangePage = showNext -> {
        int diff = showNext ? 1 : -1;
        widget.currentItem(widget.currentItem() + diff, true);
    };

    public TodoListScreen(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected State<WidgetPager> createState(Context context) {
        return StateUtils.create(new WidgetPager(context, lifecycle));
    }

    @Override
    public void initWidget(WidgetPager widget) {
        super.initWidget(widget);

        widget.adapter(new TodoListPagerAdapter(context, lifecycle, onClickChangePage))
            .currentItem(TodoListPagerAdapter.mediumPosition(), false);
    }
}
