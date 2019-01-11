package com.ittianyu.relight.loader.utils;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import com.ittianyu.relight.loader.Loader;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoaderUtils {

    public static final String DEX = "dex";

    public static View loadByConfig(ClassLoader classLoader, Context context, Lifecycle lifecycle) {
        InputStream in = classLoader.getResourceAsStream("loader.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
            String loaderClass = properties.getProperty("loader");
            Loader loader = (Loader) classLoader.loadClass(loaderClass).newInstance();
            return loader.render(context, lifecycle);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static View loadByConfig(String filePath, Context context, Lifecycle lifecycle) {
        ClassLoader classLoader = loadJar(context, filePath);
        return loadByConfig(classLoader, context, lifecycle);
    }

    public static View loadByClass(ClassLoader classLoader, Context context, Lifecycle lifecycle, String className) {
        try {
            Loader loader = (Loader) classLoader.loadClass(className).newInstance();
            return loader.render(context, lifecycle);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static View loadByClass(String filePath, Context context, Lifecycle lifecycle, String className) {
        ClassLoader classLoader = loadJar(context, filePath);
        return loadByClass(classLoader, context, lifecycle, className);
    }

    private static ClassLoader loadJar(Context context, String filePath) {
        final File optimizedDexOutputPath = context.getDir(DEX, Context.MODE_PRIVATE);
        return new DexClassLoader(filePath,
            optimizedDexOutputPath.getAbsolutePath(), null, context.getClassLoader());
    }

}
