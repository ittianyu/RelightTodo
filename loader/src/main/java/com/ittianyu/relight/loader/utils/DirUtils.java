package com.ittianyu.relight.loader.utils;

import android.content.Context;
import android.os.Environment;
import java.io.File;

public class DirUtils {
    public static final String JARS = "jars";

    public static String getDirPath(Context context, String dirName) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
            || Environment.isExternalStorageRemovable()) {
            File file = null;
            File dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            if (dir != null) {
                file = new File(dir.getPath() + File.separator + dirName);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }

            if (file != null) {
                return file.getAbsolutePath();
            }
        }

        return getDirPathInner(context, dirName);
    }

    private static String getDirPathInner(Context context, String dirName) {
        File file = new File(context.getFilesDir().getPath() + File.separator + dirName);
        if (!file.exists()) {
            file.mkdirs();
        }

        return file.getAbsolutePath();
    }


}
