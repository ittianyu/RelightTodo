package com.ittianyu.relight.todo.feature.list;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.ittianyu.relight.todo.ResLoader;
import com.ittianyu.relight.todo.common.constants.Colors;
import com.ittianyu.relight.todo.common.constants.Images;
import com.ittianyu.relight.todo.common.constants.Selectors;
import com.ittianyu.relight.todo.common.constants.Sizes;
import com.ittianyu.relight.todo.common.constants.Strings;
import com.ittianyu.relight.todo.common.widget.EmptyWidget;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.FloatingActionButtonWidget;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.native_.RecyclerWidget;
import com.ittianyu.relight.widget.native_.SwipeRefreshWidget;
import com.ittianyu.relight.widget.stateful.lcee.CommonLoadingWidget;
import com.ittianyu.relight.widget.stateful.lcee.LceeStatus;
import com.ittianyu.relight.widget.stateful.lceerm.LceermWidget;

public class TodoListScreen extends LceermWidget implements ResLoader {

    private OnClickListener onClickEmpty = v -> addTodoItem();
    private OnClickListener onClickFab = v -> addTodoItem();
    private OnClickListener onClickError = v -> reload();
    private SwipeRefreshWidget srw;

    private void addTodoItem() {

    }

    public TodoListScreen(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected Widget renderLoading() {
        return new CommonLoadingWidget(context, lifecycle);
    }

    @Override
    protected Widget renderContent() {
        srw = new SwipeRefreshWidget(context, lifecycle,
            renderRecycler()
        ).backgroundColor(getColor(Colors.color_common_bg));
        return new FrameWidget(context, lifecycle,
            srw,
            renderFab()
        ).matchParent();
    }

    @Override
    protected Widget renderEmpty() {
        return new EmptyWidget(context, lifecycle, getDrawable(context, Images.ic_empty),
            getString(Strings.todo_list_empty_text), onClickEmpty);
    }

    @Override
    protected Widget renderError() {
        return new EmptyWidget(context, lifecycle, getDrawable(context, Images.ic_error),
            getString(Strings.todo_list_error_text), onClickError);
    }

    private Widget renderFab() {
        return new FloatingActionButtonWidget(context, lifecycle)
            .imageDrawable(getDrawable(context, Images.ic_add))
            .wrapContent()
            .backgroundTintList(Selectors.primary_color_selector)
            .layoutGravity(Gravity.BOTTOM | Gravity.END)
            .marginEnd(Sizes.margin_normal)
            .marginBottom(Sizes.margin_big)
            .onClick(onClickFab);
    }

    private RecyclerWidget renderRecycler() {
        return new RecyclerWidget<RecyclerView.Adapter>(context, lifecycle) {
            @Override
            protected void initProps() {
                super.initProps();
                matchParent()
                    .layoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void update() {
                super.update();

            }
        };
    }

    @Override
    protected void onRefreshError(Throwable throwable) {
        Toast.makeText(context, getString(Strings.refresh_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRefreshComplete() {
        srw.refreshing(false);
    }

    @Override
    protected void onLoadMoreError(Throwable throwable) {
        Toast.makeText(context, getString(Strings.load_more_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onLoadMoreEmpty() {

    }

    @Override
    protected void onLoadMoreComplete() {

    }

    @Override
    protected LceeStatus onLoadData() throws Exception {
        return LceeStatus.Content;
    }

    @Override
    protected LceeStatus onLoadMore() throws Exception {
        return LceeStatus.Empty;
    }

}
