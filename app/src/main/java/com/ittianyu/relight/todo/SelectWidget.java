package com.ittianyu.relight.todo;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.ittianyu.relight.updater.Updater;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.native_.BaseLinearWidget;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.native_.material.MaterialButtonWidget;
import com.ittianyu.relight.widget.stateful.StatefulWidget;
import com.ittianyu.relight.widget.stateful.state.State;

public class SelectWidget extends StatefulWidget<LinearLayout, LinearWidget> implements OnClickListener {
    private static final int ID_CLICK_COUNT = 0;
    private static final int ID_TODO_LIST = 1;

    public SelectWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected State<LinearWidget> createState(Context context) {
        LinearWidget widget = new LinearWidget(context, lifecycle,
            new TextWidget(context, lifecycle)
                .text(context.getString(R.string.select_app))
                .marginBottom(16f),
            new MaterialButtonWidget(context, lifecycle)
                .allCaps(false)
                .id(ID_CLICK_COUNT)
                .widthMatchAndHeightWrap()
                .onClick(this)
                .text("Click Count")
                .marginBottom(16f),
            new MaterialButtonWidget(context, lifecycle)
                .allCaps(false)
                .id(ID_TODO_LIST)
                .widthMatchAndHeightWrap()
                .onClick(this)
                .text("Todo List")
        )
            .orientation(BaseLinearWidget.vertical)
            .matchParent()
            .gravity(Gravity.CENTER)
            .paddingHorizontal(16.0f);

        return StateUtils.create(widget);
    }


    @Override
    public void onClick(View v) {
        Updater updater = (Updater) this.context;
        switch (v.getId()) {
            case ID_CLICK_COUNT: {
                updater.load(Updater.CLICK_COUNT);
                break;
            }
            case ID_TODO_LIST: {
                updater.load(Updater.TODO_LIST);
                break;
            }
        }
    }

}
