package com.ittianyu.relight.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.ittianyu.relight.activity.WidgetActivity;
import com.ittianyu.relight.loader.utils.LoaderUtils;

public class MainActivity extends WidgetActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(LoaderUtils.loadByConfig(getClassLoader(), this, getLifecycle()));
    }

}
