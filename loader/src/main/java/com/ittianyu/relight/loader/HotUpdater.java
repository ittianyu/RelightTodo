package com.ittianyu.relight.loader;

public interface HotUpdater {
    String CLICK_COUNT = "clickcount";
    String TODO_LIST = "todolist";

    void hotUpdate(String name);
}
