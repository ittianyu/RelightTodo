package com.ittianyu.relight.todo;

import android.os.Bundle;
import com.ittianyu.relight.activity.WidgetActivity;
import com.ittianyu.relight.loader.Container;
import com.ittianyu.relight.loader.Loader;
import com.ittianyu.relight.loader.utils.LoaderUtils;
import com.ittianyu.relight.todo.common.callback.HotUpdater;
import com.orhanobut.logger.Logger;

public class MainActivity extends WidgetActivity implements Container, HotUpdater {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        load(LoaderUtils.getLoaderByClass(getClassLoader(), "com.ittianyu.relight.clickcount.ClickCountLoader"));
    }

    @Override
    public boolean load(Loader loader) {
        if (null == loader) {
            loader = LoaderUtils.getLoaderByConfig(getClassLoader());
        }
        try {
            setContentView(loader.render(this, getLifecycle()));
            return true;
        } catch (Throwable e) {
            Logger.e(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public void hotUpdate(String name) {
        int resId = 0;
        switch (name) {
            case "ClickCount":
//                resId = R.raw
                break;
        }
//        getResources().openRawResource(resId)
//        Files.copy()
    }
}
