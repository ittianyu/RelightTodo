package com.ittianyu.relight.todo.common.widget;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.ittianyu.relight.todo.ResLoader;
import com.ittianyu.relight.todo.common.constants.Colors;
import com.ittianyu.relight.todo.common.constants.Images;
import com.ittianyu.relight.todo.common.constants.Sizes;
import com.ittianyu.relight.todo.common.router.RouterConfig;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.native_.ToolbarWidget;
import com.ittianyu.relight.widget.stateful.navigator.Navigator;
import com.ittianyu.relight.widget.stateless.StatelessWidget;

public class CommonToolBarWidget<T extends Widget> extends StatelessWidget<LinearLayout, LinearWidget> implements ResLoader {
    private final Class<T> widgetClass;
    private final String title;
    private Object[] params;
    private Boolean showBack;
    private OnClickListener finish = v -> {
        Navigator.pop(RouterConfig.name);
    };
    private TextWidget twTitle;
    private T widget;
    private ToolbarWidget tbw;

    public CommonToolBarWidget(Context context, Lifecycle lifecycle, Class<T> widgetClass) {
        this(context, lifecycle, "", widgetClass);
    }

    public CommonToolBarWidget(Context context, Lifecycle lifecycle, Class<T> widgetClass, Object... params) {
        this(context, lifecycle, "", widgetClass, params);
    }

    public CommonToolBarWidget(Context context, Lifecycle lifecycle, String title, Class<T> widgetClass) {
        this(context, lifecycle, title, null, widgetClass);
    }

    public CommonToolBarWidget(Context context, Lifecycle lifecycle, String title, Class<T> widgetClass, Object... params) {
        this(context, lifecycle, title, null, widgetClass, params);
    }

    public CommonToolBarWidget(Context context, Lifecycle lifecycle, String title, Boolean showBack, Class<T> widgetClass) {
        super(context, lifecycle);
        this.widgetClass = widgetClass;
        this.title = title;
        this.showBack = showBack;
    }

    public CommonToolBarWidget(Context context, Lifecycle lifecycle, String title, Boolean showBack, Class<T> widgetClass, Object... params) {
        super(context, lifecycle);
        this.widgetClass = widgetClass;
        this.title = title;
        this.showBack = showBack;
        this.params = params;
    }

    @Override
    protected LinearWidget build(Context context) {
        if (null == showBack) {
            showBack = Navigator.get(RouterConfig.name).size() > 1;
        }
        if (this.params == null) {
            this.params = new Object[0];
        }

        Object[] params = new Object[this.params.length + 2];
        Class[] classes = new Class[this.params.length + 2];
        params[0] = context;
        params[1] = lifecycle;
        classes[0] = Context.class;
        classes[1] = Lifecycle.class;
        for (int i = 0; i < this.params.length; i++) {
            params[i + 2] = this.params[i];
            classes[i + 2] = this.params[i].getClass();
        }
        try {
            widget = widgetClass.getConstructor(classes).newInstance(params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        twTitle = new TextWidget(context, lifecycle)
            .text(title)
            .textColor(Color.WHITE)
            .textSize(Sizes.text_title5)
            .typeface(null, Typeface.BOLD);

        tbw = new ToolbarWidget(context, lifecycle,
            twTitle.layoutGravity(Gravity.CENTER)
        )
            .elevation(Sizes.elevation_normal)
            .backgroundColor(getColor(Colors.color_primary))
            .navigationIcon(showBack ? getDrawable(context, Images.ic_back) : null)
            .navigationOnClick(showBack ? finish : null);

        return new LinearWidget(context, lifecycle,
            tbw,
            widget
        )
            .orientation(LinearWidget.vertical)
            .matchParent();
    }

    public CommonToolBarWidget title(CharSequence text) {
        twTitle.text(text);
        return this;
    }

    public T widget() {
        return widget;
    }

    public CommonToolBarWidget addChildren(Widget... widget) {
        tbw.addChildren(widget);
        return this;
    }
}
