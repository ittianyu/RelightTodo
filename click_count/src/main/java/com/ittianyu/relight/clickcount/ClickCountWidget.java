package com.ittianyu.relight.clickcount;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import com.ittianyu.relight.clickcount.constants.Selectors;
import com.ittianyu.relight.clickcount.constants.Sizes;
import com.ittianyu.relight.clickcount.constants.Strings;
import com.ittianyu.relight.loader.Container;
import com.ittianyu.relight.loader.utils.DirUtils;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.native_.BaseLinearWidget;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.material.MaterialButtonWidget;
import com.ittianyu.relight.widget.stateful.StatefulWidget;
import com.ittianyu.relight.widget.stateful.state.State;
import java.io.File;

public class ClickCountWidget extends StatefulWidget<LinearLayout, LinearWidget> implements ResLoader {
    private int count = 1;
    private MaterialButtonWidget bwCount;

    public ClickCountWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected State<LinearWidget> createState(Context context) {
        return StateUtils.create(renderRoot());
    }

    private LinearWidget renderRoot() {
        bwCount = new MaterialButtonWidget(context, lifecycle)
            .widthMatchAndHeightWrap()
            .backgroundTintList(Selectors.primary_color_selector)
            .onClick(v -> setState(() -> count++));

        MaterialButtonWidget bwRestore = new MaterialButtonWidget(context, lifecycle)
            .widthMatchAndHeightWrap()
            .marginTop(Sizes.margin_normal)
            .backgroundTintList(Selectors.primary_color_selector)
            .text(getString(Strings.click_count_restore))
            .onClick(v -> restore());

        return new LinearWidget(context, lifecycle,
            bwCount,
            bwRestore
        )
            .orientation(BaseLinearWidget.vertical)
            .matchParent()
            .gravity(Gravity.CENTER)
            .paddingHorizontal(Sizes.margin_normal);
    }

    @Override
    public void initWidget(LinearWidget widget) {
        super.initWidget(widget);
        update();
    }

    @Override
    public void update() {
        super.update();
        bwCount.text(String.valueOf(count));
    }

    private void restore() {
        // delete local jar
        String path = DirUtils.getDirPath(context, DirUtils.JARS);
        File file = new File(path, Strings.loader_name + ".jar");
        if (file.exists()) {
            file.delete();
        }
        // load
        if (context instanceof Container) {
            ((Container) context).load(null);
        }
    }
}
