package com.ittianyu.relight.todo.common.constants;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import com.ittianyu.dynamicupdater.loader.ResourceLoader;
import com.ittianyu.relight.utils.StateListBuilder;

public interface Selectors {
    ColorStateList primary_color_selector = StateListBuilder.colorBuilder()
        .pressed(getColor(Colors.color_primary_dark))
        .normal(getColor(Colors.color_primary))
        .build();
    ColorStateList pink_color_selector = StateListBuilder.colorBuilder()
        .pressed(getColor(Colors.color_common_pink_dark))
        .normal(getColor(Colors.color_common_pink))
        .build();
    ColorStateList red_color_selector = StateListBuilder.colorBuilder()
        .pressed(getColor(Colors.color_common_red_dark))
        .normal(getColor(Colors.color_common_red))
        .build();
    ColorStateList white = ColorStateList.valueOf(Color.WHITE);
    ColorStateList black = ColorStateList.valueOf(Color.BLACK);



    static String getString(String name) {
        return ResourceLoader.getInstance(Selectors.class.getClassLoader()).getString(name);
    }

    static Drawable getDrawable(Context context, String name) {
        return ResourceLoader.getInstance(Selectors.class.getClassLoader()).getDrawable(context, name);
    }

    static Integer getColor(String name) {
        return ResourceLoader.getInstance(Selectors.class.getClassLoader()).getColor(name);
    }
}
