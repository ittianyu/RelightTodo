package com.ittianyu.relight.updater;

public interface Updater {
    String CLICK_COUNT = "clickcount.apk";
    String TODO_LIST = "todolist.apk";

    void restore();
    void load(String name);
}
