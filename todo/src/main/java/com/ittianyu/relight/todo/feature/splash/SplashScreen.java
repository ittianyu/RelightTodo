package com.ittianyu.relight.todo.feature.splash;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import com.ittianyu.relight.todo.ResLoader;
import com.ittianyu.relight.todo.common.constants.Colors;
import com.ittianyu.relight.todo.common.constants.Images;
import com.ittianyu.relight.todo.common.constants.Sizes;
import com.ittianyu.relight.todo.common.constants.Strings;
import com.ittianyu.relight.todo.common.datasource.local.LocalTaskDataSource;
import com.ittianyu.relight.todo.common.router.RouterConfig;
import com.ittianyu.relight.widget.native_.ImageWidget;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateful.navigator.WidgetNavigator;
import com.ittianyu.relight.widget.stateless.StatelessWidget;

public class SplashScreen extends StatelessWidget<LinearLayout, LinearWidget> implements ResLoader {
    private static final long SHOW_TIME = 1000 * 2;

    public SplashScreen(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected LinearWidget build(Context context) {
        return new LinearWidget(context, lifecycle,
            new ImageWidget(context, lifecycle).imageDrawable(getDrawable(context, Images.ic_logo)).marginBottom(Sizes.margin_normal),
            new TextWidget(context, lifecycle).text(getString(Strings.splash_center_title)).textColor(Color.WHITE).textSize(Sizes.text_title6)
        ).matchParent().backgroundColor(getColor(Colors.color_primary)).orientation(LinearWidget.vertical).gravity(Gravity.CENTER);
    }

    @Override
    public void initWidget(LinearWidget widget) {
        super.initWidget(widget);
        widget.render().postDelayed(() -> {
            WidgetNavigator.replace(RouterConfig.name, RouterConfig.toDoList);
        }, SHOW_TIME);

        LocalTaskDataSource ds = LocalTaskDataSource.getInstance(context);
    }
}
