package com.ittianyu.relight.todo.common.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ittianyu.relight.widget.Widget;
import java.util.List;

public abstract class WidgetAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    private final Widget widget;

    public WidgetAdapter(Widget widget, @Nullable List<T> data) {
        super(data);
        this.widget = widget;
    }

    @Override
    protected View getItemView(@LayoutRes int layoutResId, ViewGroup parent) {
        return widget.render();
    }
}
