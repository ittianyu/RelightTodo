package com.ittianyu.relight.todo.common.utils;

public class CheckUtils {

    /**
     *
     * @param str
     * @param min include min
     * @param max include max
     * @return
     */
    public static boolean lengthBetween(CharSequence str, int min, int max) {
        if (str == null)
            return false;
        int length = str.length();
        return length >= min && length <= max;
    }

}
