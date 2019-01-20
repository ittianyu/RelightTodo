package com.ittianyu.relight.todo.feature.list;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.v4.view.ViewPager;
import com.ittianyu.relight.todo.ResLoader;
import com.ittianyu.relight.todo.common.callback.OnClick1;
import com.ittianyu.relight.todo.common.widget.QuickScrollWidgetPager;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.stateful.StatefulWidget;
import com.ittianyu.relight.widget.stateful.state.State;

public class TodoListScreen extends StatefulWidget<ViewPager, QuickScrollWidgetPager> implements ResLoader {
    private TodoListPagerAdapter adapter;
    private OnClick1<Integer> onClickChangePage = diff -> {
        int index = widget.currentItem() + diff;
        if (Math.abs(diff) <= 1) {
            widget.currentItem(index);
        } else {
            adapter.newWidgets();
            widget.currentItem(index, false);
        }
    };
    public TodoListScreen(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected State<QuickScrollWidgetPager> createState(Context context) {
        return StateUtils.create(new QuickScrollWidgetPager(context, lifecycle));
    }

    @Override
    public void initWidget(QuickScrollWidgetPager widget) {
        super.initWidget(widget);

        adapter = newAdapter();
        widget.adapter(adapter)
            .currentItem(TodoListPagerAdapter.mediumPosition(), false);
    }

    private TodoListPagerAdapter newAdapter() {
        return new TodoListPagerAdapter(context, lifecycle, onClickChangePage);
    }
}
