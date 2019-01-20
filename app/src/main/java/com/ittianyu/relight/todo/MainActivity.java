package com.ittianyu.relight.todo;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.ittianyu.relight.activity.WidgetActivity;
import com.ittianyu.relight.loader.Container;
import com.ittianyu.relight.loader.HotUpdater;
import com.ittianyu.relight.loader.Loader;
import com.ittianyu.relight.loader.utils.DirUtils;
import com.ittianyu.relight.loader.utils.FileUtils;
import com.ittianyu.relight.loader.utils.LoaderUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.InputStream;

public class MainActivity extends WidgetActivity implements Container, HotUpdater {
    private static final String NAME = "loader";
    private static final String KEY_JAR = "jar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSp();
        String jar = sp.getString(KEY_JAR, CLICK_COUNT);
        hotUpdate(jar);
    }

    private SharedPreferences getSp() {
        return getSharedPreferences(NAME, MODE_PRIVATE);
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
            case CLICK_COUNT:
                resId = R.raw.clickcount;
                break;
            case TODO_LIST:
                resId = R.raw.todolist;
                break;
        }
        String dir = DirUtils.getDirPath(this, DirUtils.JARS);
        File file = new File(dir, name + ".jar");
        if (file.exists()) {
            loadByFile(file);
            saveConfig(name);
            return;
        }

        InputStream in = getResources().openRawResource(resId);
        if (FileUtils.copyToFile(in, file)) {
            loadByFile(file);
            saveConfig(name);
        }
    }

    private void saveConfig(String name) {
        SharedPreferences sp = getSp();
        sp.edit().putString(KEY_JAR, name).apply();
    }

    private void loadByFile(File file) {
        Loader loader = LoaderUtils.getLoaderByConfig(file.getAbsolutePath(), this);
        load(loader);
    }
}
