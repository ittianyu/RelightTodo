package com.ittianyu.relight.todo.feature.list.list;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.ittianyu.relight.thread.Runnable1;
import com.ittianyu.relight.todo.ResLoader;
import com.ittianyu.relight.todo.common.constants.Colors;
import com.ittianyu.relight.todo.common.constants.Drawables;
import com.ittianyu.relight.todo.common.constants.Images;
import com.ittianyu.relight.todo.common.constants.Selectors;
import com.ittianyu.relight.todo.common.constants.Sizes;
import com.ittianyu.relight.todo.common.constants.Strings;
import com.ittianyu.relight.todo.common.datasource.entiry.SortEntry;
import com.ittianyu.relight.todo.common.datasource.entiry.Task;
import com.ittianyu.relight.todo.common.datasource.entiry.TaskWithTags;
import com.ittianyu.relight.todo.common.datasource.local.LocalTaskDataSource;
import com.ittianyu.relight.todo.common.router.AddTodoRoute;
import com.ittianyu.relight.todo.common.router.RouterConfig;
import com.ittianyu.relight.todo.common.utils.DateUtils;
import com.ittianyu.relight.todo.common.utils.PageUtils;
import com.ittianyu.relight.todo.common.widget.EmptyWidget;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.FloatingActionButtonWidget;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.native_.RecyclerWidget;
import com.ittianyu.relight.widget.native_.SwipeRefreshWidget;
import com.ittianyu.relight.widget.stateful.lcee.CommonLoadingWidget;
import com.ittianyu.relight.widget.stateful.lcee.LceeStatus;
import com.ittianyu.relight.widget.stateful.lceerm.LceermWidget;
import com.ittianyu.relight.widget.stateful.navigator.WidgetNavigator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TodoListWidget extends LceermWidget implements ResLoader {
    private static final List<SortEntry> sorts =  Arrays.asList(
        new SortEntry("status"),
        new SortEntry("priority", SortEntry.ORDER_DESC),
        new SortEntry("end_time"),
        new SortEntry("create_time")
    );
    private long startTime = DateUtils.getTodayStartTime();
    private long endTime = DateUtils.getTodayEndTime();
    private List<TaskWithTags> data;

    private OnClickListener onClickEmpty = v -> addTodoItem();
    private OnClickListener onClickFab = v -> addTodoItem();
    private OnClickListener onClickError = v -> reload();
    private SwipeRefreshWidget srw;
    private RecyclerWidget<TodoListAdapter> rw;
    private TodoListAdapter adapter;

    public TodoListWidget(Context context, Lifecycle lifecycle) {
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
        )
            .backgroundColor(getColor(Colors.color_common_bg))
            .onRefresh(this::refresh);
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
        adapter = new TodoListAdapter(context, lifecycle, Collections.emptyList());
        adapter.setEnableLoadMore(true);
        DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(Drawables.divider_gray_1px);
        rw = new RecyclerWidget<TodoListAdapter>(context, lifecycle)
            .layoutManager(new LinearLayoutManager(context))
            .adapter(adapter)
            .addItemDecoration(decoration);
        rw
            .matchParent()
            .onUpdate(() -> {
                if (status != LceeStatus.Content) {
                    return;
                }
                switch (loadType) {
                    case FirstLoad:
                    case Refresh:
                        adapter.setNewData(data);
                        break;
                    case LoadMore:
                        adapter.addData(data);
                        break;
                }
            });
        return rw;
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
        rw.adapter.loadMoreFail();
    }

    @Override
    protected void onLoadMoreEmpty() {
        rw.adapter.loadMoreEnd();
    }

    @Override
    protected void onLoadMoreComplete() {
        rw.adapter.loadMoreComplete();
    }

    @Override
    protected LceeStatus onLoadData() throws Exception {
        return getData(1);
    }


    @NonNull
    private LceeStatus getData(int page) {
        data = LocalTaskDataSource
            .getInstance(context).query(page, startTime, endTime);
        if (data.isEmpty()) {
            return LceeStatus.Empty;
        }
        return LceeStatus.Content;
    }

    @Override
    protected LceeStatus onLoadMore() throws Exception {
        return getData(PageUtils.getPage(rw.adapter.getData().size(), LocalTaskDataSource.COUNT_PER_PAGE));
    }

    private void addTodoItem() {
        TaskWithTags data = new TaskWithTags();
        Task task = new Task();
        task.setStartTime(startTime);
        task.setEndTime(endTime);
        data.setTask(task);
        WidgetNavigator.push(RouterConfig.name, new AddTodoRoute(data), new Runnable1<Boolean>() {
            @Override
            public void run(Boolean data) {
                if (data != null && data) {
                    if (status == LceeStatus.Content) {
                        refresh();
                    } else {
                        reload();
                    }
                }
            }
        });
    }

    public TodoListWidget startTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public TodoListWidget endTime(long endTime) {
        this.endTime = endTime;
        return this;
    }

}
