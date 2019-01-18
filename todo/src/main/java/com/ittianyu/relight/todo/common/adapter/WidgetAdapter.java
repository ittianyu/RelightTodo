package com.ittianyu.relight.todo.common.adapter;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ittianyu.relight.widget.Widget;
import java.util.List;

public abstract class WidgetAdapter<T, K extends WidgetHolder<W>, W extends Widget> extends BaseQuickAdapter<T, K> {
    protected final Context context;
    protected final Lifecycle lifecycle;

    public WidgetAdapter(Context context, Lifecycle lifecycle, @Nullable List<T> data) {
        super(data);
        this.context = context;
        this.lifecycle = lifecycle;
    }

    protected abstract W renderItem(ViewGroup parent);

    @Override
    protected View getItemView(@LayoutRes int layoutResId, ViewGroup parent) {
        Widget widget = renderItem(parent);
        View view = widget.render();
        view.setTag(widget);
        return view;
    }

    protected abstract void convert(WidgetHolder<W> helper, T item);
}
