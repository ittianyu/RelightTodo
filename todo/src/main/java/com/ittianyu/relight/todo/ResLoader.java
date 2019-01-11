package com.ittianyu.relight.todo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.ittianyu.relight.loader.ResourceLoader;

public interface ResLoader {
    default String getString(String name) {
        return ResourceLoader.getInstance(getClass().getClassLoader()).getString(name);
    }

    default Drawable getDrawable(Context context, String name) {
        return ResourceLoader.getInstance(getClass().getClassLoader()).getDrawable(context, name);
    }

    default Integer getColor(String name) {
        return ResourceLoader.getInstance(getClass().getClassLoader()).getColor(name);
    }

}
