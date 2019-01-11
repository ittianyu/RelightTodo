package com.ittianyu.relight.todo.common.widget;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.ittianyu.relight.todo.ResLoader;
import com.ittianyu.relight.todo.common.constants.Colors;
import com.ittianyu.relight.todo.common.constants.Sizes;
import com.ittianyu.relight.widget.native_.ImageWidget;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateless.StatelessWidget;

public class EmptyWidget extends StatelessWidget<LinearLayout, LinearWidget> implements ResLoader {

    private final Drawable icon;
    private final String text;
    private final OnClickListener onClickListener;

    public EmptyWidget(Context context, Lifecycle lifecycle, Drawable icon, String text,
        View.OnClickListener onClickListener) {
        super(context, lifecycle);
        this.icon = icon;
        this.text = text;
        this.onClickListener = onClickListener;
    }

    @Override
    protected LinearWidget build(Context context) {
        return new LinearWidget(context, lifecycle,
            new ImageWidget(context, lifecycle).imageDrawable(icon)
                .marginBottom(Sizes.margin_normal),
            new TextWidget(context, lifecycle).text(text).textColor(Color.BLACK)
                .textSize(Sizes.text_title3)
        )
            .matchParent()
            .backgroundColor(getColor(Colors.color_common_bg))
            .orientation(LinearWidget.vertical)
            .gravity(Gravity.CENTER)
            .onClick(onClickListener);
    }

}
