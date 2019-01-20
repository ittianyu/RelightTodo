package com.ittianyu.relight.todo.feature;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.ittianyu.relight.thread.Runnable1;
import com.ittianyu.relight.todo.ResLoader;
import com.ittianyu.relight.todo.common.constants.Colors;
import com.ittianyu.relight.todo.common.constants.Drawables;
import com.ittianyu.relight.todo.common.constants.Sizes;
import com.ittianyu.relight.todo.common.router.RouterConfig;
import com.ittianyu.relight.todo.common.router.TextMenuRoute;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.BaseLinearWidget;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateful.navigator.WidgetNavigator;
import com.ittianyu.relight.widget.stateless.StatelessWidget;
import com.orhanobut.logger.Logger;

public class MenuScreen extends StatelessWidget<FrameLayout, FrameWidget> implements ResLoader {
    private final String[] items;

    public MenuScreen(Context context, Lifecycle lifecycle, String... items) {
        super(context, lifecycle);
        this.items = items;
    }

    @Override
    protected FrameWidget build(Context context) {
        return new FrameWidget(context, lifecycle,
            new LinearWidget(context, lifecycle,
                renderItems()
            )
                .showDividers(LinearLayout.SHOW_DIVIDER_MIDDLE)
                .dividerDrawable(Drawables.divider_gray_1px)
                .layoutGravity(Gravity.CENTER)
                .marginHorizontal(Sizes.margin_big1)
                .widthMatchAndHeightWrap()
                .elevation(Integer.MAX_VALUE / 1.1f)
                .orientation(BaseLinearWidget.vertical)
        )
            .matchParent()
            .backgroundColor(getColor(Colors.color_translucent_gray3))
            .onClick(v -> {
                WidgetNavigator.pop(RouterConfig.name, null);
            });
    }

    private Widget[] renderItems() {
        Widget[] widgets = new Widget[items.length];
        for (int i = 0; i < items.length; i++) {
            Integer index = i;
            widgets[i] = new TextWidget(context, lifecycle)
                .text(items[i])
                .textColor(getColor(Colors.color_black))
                .textSize(Sizes.text_title6)
                .widthMatchAndHeightWrap()
                .backgroundColor(getColor(Colors.color_white))
                .padding(Sizes.margin_normal)
                .onClick(v -> {
                    WidgetNavigator.pop(RouterConfig.name, null, index);
                });
        }
        return widgets;
    }

    public static void show(Runnable1<Integer> callback, String... items) {
        WidgetNavigator.push(RouterConfig.name, new TextMenuRoute(items), null, callback);
    }
}
