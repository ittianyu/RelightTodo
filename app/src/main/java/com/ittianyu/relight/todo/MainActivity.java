package com.ittianyu.relight.todo;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.ittianyu.dynamicupdater.loader.Container;
import com.ittianyu.dynamicupdater.loader.Loader;
import com.ittianyu.dynamicupdater.loader.utils.DirUtils;
import com.ittianyu.dynamicupdater.loader.utils.FileUtils;
import com.ittianyu.dynamicupdater.loader.utils.LoaderUtils;
import com.ittianyu.relight.activity.WidgetActivity;
import com.ittianyu.relight.updater.Updater;
import com.ittianyu.relight.utils.WidgetUtils;
import com.orhanobut.logger.Logger;
import java.io.File;
import java.io.InputStream;


public class MainActivity extends WidgetActivity implements Container, Updater {
    private static final String NAME = "loader";
    private static final String KEY_APK = "apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSp();
        String apkName = sp.getString(KEY_APK, "");
        load(apkName);
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
    public void restore() {
        saveConfig("");
        load("");
    }

    @Override
    public void load(String name) {
        int resId = 0;
        switch (name) {
            case CLICK_COUNT:
                resId = R.raw.clickcount;
                break;
            case TODO_LIST:
                resId = R.raw.todolist;
                break;
            default:
                setContentView(WidgetUtils.render(this, SelectWidget.class));
                return;
        }
        String dir = DirUtils.getDirPath(this, KEY_APK);
        File file = new File(dir, name);
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
        sp.edit().putString(KEY_APK, name).apply();
    }

    private void loadByFile(File file) {
        Loader loader = LoaderUtils.getLoaderByConfig(file.getAbsolutePath(), this);
        load(loader);
    }
}
