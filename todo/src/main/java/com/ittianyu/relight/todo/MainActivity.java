package com.ittianyu.relight.todo;

import android.os.Bundle;
import com.ittianyu.dynamicupdater.loader.utils.LoaderUtils;
import com.ittianyu.relight.activity.WidgetActivity;

public class MainActivity extends WidgetActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LoaderUtils.loadByConfig(getClassLoader(), this, getLifecycle()));
    }
}
