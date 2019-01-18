package com.ittianyu.relight.todo.common.utils;

public class PageUtils {

    /**
     *
     * @param currentCount
     * @param countPerPage
     * @return page (start from 1)
     */
    public static int getPage(int currentCount, int countPerPage) {
        return (currentCount + countPerPage - 1) / countPerPage + 1;
    }

}
