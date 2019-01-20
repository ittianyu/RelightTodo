package com.ittianyu.relight.todo.feature.list;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.ittianyu.relight.todo.ResLoader;
import com.ittianyu.relight.todo.common.callback.OnClick1;
import com.ittianyu.relight.todo.common.constants.Images;
import com.ittianyu.relight.todo.common.constants.Sizes;
import com.ittianyu.relight.todo.common.utils.DateUtils;
import com.ittianyu.relight.todo.common.widget.CommonToolBarWidget;
import com.ittianyu.relight.todo.feature.list.list.TodoListWidget;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.ImageWidget;
import com.ittianyu.relight.widget.native_.WidgetPagerAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TodoListPagerAdapter extends WidgetPagerAdapter<CommonToolBarWidget> implements ResLoader, OnClickListener {
    private static final int REAL_COUNT = 4;
    private static final int ID_IW_LEFT = 1;
    private static final int ID_IW_RIGHT = 2;
    private final OnClick1<Integer> onClickChangePage;

    private List<CommonToolBarWidget<TodoListWidget>> widgets = new ArrayList<>(REAL_COUNT);

    public TodoListPagerAdapter(Context context, Lifecycle lifecycle, OnClick1<Integer> onClickChangePage) {
        super(context, lifecycle);
        this.onClickChangePage = onClickChangePage;
        newWidgets();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    public void newWidgets() {
        widgets.clear();
        for (int i = 0; i < REAL_COUNT; i++) {
            widgets.add(newWidget());
        }
    }

    private CommonToolBarWidget<TodoListWidget> newWidget() {
        CommonToolBarWidget<TodoListWidget> widget = new CommonToolBarWidget<>(
            context, lifecycle, "", false, TodoListWidget.class);
        widget.render();
        widget.addChildren(
            new ImageWidget(context, lifecycle).id(ID_IW_LEFT).imageDrawable(getDrawable(context, Images.ic_arrow_left))
                .onClick(this).padding(Sizes.margin_small2).layoutGravity(Gravity.CENTER_VERTICAL),
            new ImageWidget(context, lifecycle).id(ID_IW_RIGHT).imageDrawable(getDrawable(context, Images.ic_arrow_right))
                .onClick(this).padding(Sizes.margin_small2).marginEnd(Sizes.margin_normal).layoutGravity(Gravity.END | Gravity.CENTER_VERTICAL)
        );
        return widget;
    }

    @Override
    protected CommonToolBarWidget getItem(int position) {
        long time = getTime(position);
        CommonToolBarWidget<TodoListWidget> widget = widgets.get(position % REAL_COUNT);
//        CommonToolBarWidget<TodoListWidget> widget = newWidget();
        widget.onTitleClick(v -> {
            selectDate(time);
        });
        TodoListWidget todoListWidget = (TodoListWidget) widget.title(DateUtils.formatDate(time)).widget();
        todoListWidget
            .startTime(DateUtils.getDateStartTime(time))
            .endTime(DateUtils.getDateEndTime(time))
            .reload();
        return widget;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public static int mediumPosition() {
        return Integer.MAX_VALUE / 2;
    }

    private long getTime(int position) {
        int diff = position - mediumPosition();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DAY_OF_MONTH, diff);
        return calendar.getTimeInMillis();
    }

    @Override
    public void onClick(View v) {
        if (null == onClickChangePage) {
            return;
        }
        int diff = 0;
        switch (v.getId()) {
            case ID_IW_LEFT:
                diff = -1;
                break;
            case ID_IW_RIGHT:
                diff = 1;
                break;
        }
        onClickChangePage.onClick(diff);
    }

    private void selectDate(long time) {
        if (onClickChangePage == null) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        DatePickerDialog dialog = new DatePickerDialog(context,
            (view, year, month, dayOfMonth) -> {
                long targetTime = DateUtils.getDateStartTime(year, month, dayOfMonth);
                int diff = (int) ((targetTime - DateUtils.getDateStartTime(time)) / DateUtils.DAY_MS);
                onClickChangePage.onClick(diff);
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
}
