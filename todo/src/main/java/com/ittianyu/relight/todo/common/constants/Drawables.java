package com.ittianyu.relight.todo.common.constants;

import android.graphics.drawable.Drawable;
import com.ittianyu.relight.loader.ResourceLoader;
import com.ittianyu.relight.utils.ShapeDrawableBuilder;

public interface Drawables {
    Drawable divider_gray_1px = new ShapeDrawableBuilder()
            .color(getColor(Colors.color_translucent_gray2))
            .height(1)
            .build();
    static Integer getColor(String name) {
        return ResourceLoader.getInstance(Drawables.class.getClassLoader()).getColor(name);
    }
}
