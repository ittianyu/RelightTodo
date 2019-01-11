package com.ittianyu.relight.todo.common.constants;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import com.ittianyu.relight.loader.ResourceLoader;
import com.ittianyu.relight.todo.ResLoader;
import com.ittianyu.relight.utils.StateListBuilder;

public interface Selectors {
    ColorStateList primary_color_selector = StateListBuilder.colorBuilder()
        .pressed(getColor(Colors.color_primary_dark))
        .normal(getColor(Colors.color_primary))
        .build();



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
