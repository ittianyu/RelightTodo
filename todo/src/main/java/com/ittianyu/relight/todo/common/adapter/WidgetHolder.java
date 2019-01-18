package com.ittianyu.relight.todo.common.adapter;

import android.view.View;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ittianyu.relight.widget.Widget;

public class WidgetHolder<T extends Widget> extends BaseViewHolder {
    public T widget;

    public WidgetHolder(View view) {
        super(view);
        widget = (T) view.getTag();
    }
}
