package com.ittianyu.relight.todo.feature.add;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.ittianyu.relight.todo.ResLoader;
import com.ittianyu.relight.todo.common.constants.Colors;
import com.ittianyu.relight.todo.common.constants.Images;
import com.ittianyu.relight.todo.common.constants.Selectors;
import com.ittianyu.relight.todo.common.constants.Sizes;
import com.ittianyu.relight.todo.common.constants.Strings;
import com.ittianyu.relight.todo.common.datasource.entiry.Tag;
import com.ittianyu.relight.todo.common.datasource.entiry.Task;
import com.ittianyu.relight.todo.common.datasource.entiry.TaskWithTags;
import com.ittianyu.relight.todo.common.datasource.local.LocalTaskDataSource;
import com.ittianyu.relight.todo.common.router.RouterConfig;
import com.ittianyu.relight.todo.common.utils.CheckUtils;
import com.ittianyu.relight.todo.common.utils.DateUtils;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.RelativeWidget;
import com.ittianyu.relight.widget.native_.material.ChipGroupWidget;
import com.ittianyu.relight.widget.native_.material.ChipWidget;
import com.ittianyu.relight.widget.native_.material.ChipWidget.Style;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.native_.material.MaterialButtonWidget;
import com.ittianyu.relight.widget.native_.material.TextInputEditWidget;
import com.ittianyu.relight.widget.native_.material.TextInputWidget;
import com.ittianyu.relight.widget.stateful.StatefulWidget;
import com.ittianyu.relight.widget.stateful.navigator.WidgetNavigator;
import com.ittianyu.relight.widget.stateful.state.State;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTodoScreen extends StatefulWidget<LinearLayout, LinearWidget> implements ResLoader {
    private static final int LEN_TITLE_MAX = 64;

    private TextInputEditWidget ewTitle;
    private TextInputEditWidget ewDesc;
    private TextWidget twStartTime;
    private TextWidget twEndTime;
    private ChipGroupWidget cgwPriority;
    private TextInputEditWidget ewTags;

    private enum Action {
        UpdateView,
        CheckData,
        AddData,
    }
    private Action action = Action.UpdateView;
    private boolean actionResult;
    private TaskWithTags data;

    public AddTodoScreen(Context context, Lifecycle lifecycle) {
        this(context, lifecycle, null);
    }

    public AddTodoScreen(Context context, Lifecycle lifecycle, TaskWithTags data) {
        super(context, lifecycle);
        this.data = data;
        if (data == null) {
            Task task = new Task();
            task.setPriority(Task.PRIORITY_NORMAL);
            task.setStartTime(DateUtils.getTodayStartTime());
            task.setEndTime(DateUtils.getTodayEndTime());
            this.data = new TaskWithTags(task, null);
        }
    }

    @Override
    protected State<LinearWidget> createState(Context context) {
        LinearWidget linearWidget = new LinearWidget(context, lifecycle,
            renderTitle(),
            renderDesc(),
            renderTime(),
            renderPriority(),
            renderTags(),
            renderOk()
        )
            .matchParent()
            .paddingHorizontal(Sizes.margin_normal)
            .paddingTop(Sizes.margin_normal)
            .orientation(LinearWidget.vertical)
            .backgroundColor(Color.WHITE);
        return StateUtils.create(linearWidget);
    }

    private Widget renderTitle() {
        ewTitle = new TextInputEditWidget(context, lifecycle)
                .widthMatchAndHeightWrap()
                .maxLines(1);
        return new TextInputWidget(context, lifecycle,
            ewTitle
        )
            .widthMatchAndHeightWrap()
            .hint(getString(Strings.add_todo_edit_hint_title))
            .counterMaxLength(LEN_TITLE_MAX)
            .counterEnabled(true);
    }

    private Widget renderDesc() {
        ewDesc = new TextInputEditWidget(context, lifecycle).maxLines(5).lines(1)
            .widthMatchAndHeightWrap();
        return new TextInputWidget(context, lifecycle,
            ewDesc
        )
            .widthMatchAndHeightWrap()
            .hint(getString(Strings.add_todo_edit_hint_desc))
            .marginBottom(Sizes.margin_normal);
    }

    private ChipGroupWidget renderPriority() {
        cgwPriority = new ChipGroupWidget(context, lifecycle,
            new ChipWidget(context, lifecycle).style(Style.Filter)
                .text(getString(Strings.priority_low))
                .id((int) Task.PRIORITY_LOW)
                .textColor(getColor(Colors.color_common_gray1))
                .checkedIconTint(Selectors.black),
            new ChipWidget(context, lifecycle).style(Style.Filter)
                .text(getString(Strings.priority_normal))
                .id((int) Task.PRIORITY_NORMAL)
                .checkedIconTint(Selectors.black),
            new ChipWidget(context, lifecycle).style(Style.Filter)
                .text(getString(Strings.priority_high))
                .id((int) Task.PRIORITY_HIGH)
                .chipBackgroundColor(Selectors.pink_color_selector)
                .textColor(Color.WHITE)
                .checkedIconTint(Selectors.white),
            new ChipWidget(context, lifecycle).style(Style.Filter)
                .text(getString(Strings.priority_urgent))
                .id((int) Task.PRIORITY_URGENT)
                .chipBackgroundColor(Selectors.red_color_selector)
                .textColor(Color.WHITE)
                .checkedIconTint(Selectors.white)
        )
            .singleSelection(true)
            .marginBottom(Sizes.margin_normal)
            .onCheckedChange((chipGroup, id) -> {
                data.getTask().setPriority((short) id);
            }).widthMatchAndHeightWrap();
        return cgwPriority;
    }

    private Widget renderTime() {
        twStartTime = new TextWidget(context, lifecycle)
            .textSize(Sizes.text_content)
            .drawableLeft(getDrawable(context, Images.ic_date_start))
            .onClick(v -> {
                selectDate(true);
            });
        twEndTime = new TextWidget(context, lifecycle)
            .textSize(Sizes.text_content)
            .drawableLeft(getDrawable(context, Images.ic_date_end))
            .onClick(v -> {
                selectDate(false);
            });

        return new RelativeWidget(context, lifecycle,
            twStartTime,
            twEndTime.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
        )
            .widthMatchAndHeightWrap()
            .marginBottom(Sizes.margin_normal);
    }

    private Widget renderTags() {
        ewTags = new TextInputEditWidget(context, lifecycle).maxLines(3).lines(1)
            .widthMatchAndHeightWrap();
        return new TextInputWidget(context, lifecycle,
            ewTags
        )
            .widthMatchAndHeightWrap()
            .hint(getString(Strings.add_todo_edit_hint_tags))
            .marginBottom(Sizes.margin_normal);
    }

    private MaterialButtonWidget renderOk() {
        return new MaterialButtonWidget(context, lifecycle)
            .widthMatchAndHeightWrap()
            .backgroundTintList(Selectors.primary_color_selector)
            .text(getString(Strings.button_ok))
            .onClick(v -> setState(() -> {
                action = Action.CheckData;
            }));
    }

    @Override
    public void initWidget(LinearWidget widget) {
        super.initWidget(widget);

        update();
    }

    @Override
    public void update() {
        super.update();
        switch (action) {
            case UpdateView:
                updateView();
                break;
            case CheckData:
                checkData();
                break;
            case AddData:
                addData();
                break;
            default:
                break;
        }

        action = Action.UpdateView;
    }

    private void updateView() {
        ewTitle.text(data.getTask().getTitle());
        ewDesc.text(data.getTask().getDescription());
        twStartTime.text(DateUtils.formatDate(data.getTask().getStartTime()));
        twEndTime.text(DateUtils.formatDate(data.getTask().getEndTime()));
        cgwPriority.check(data.getTask().getPriority());
        ewTags.text(getTagsStr());
    }

    private String getTagsStr() {
        List<Tag> tags = data.getTags();
        if (tags == null) {
            return "";
        }
        return TextUtils.join(",", tags);
    }

    private void checkData() {
        // title length
        if (!CheckUtils.lengthBetween(ewTitle.text(), 1, LEN_TITLE_MAX)) {
            Toast.makeText(context, getString(Strings.add_todo_invalid_tips_title_length, 1, LEN_TITLE_MAX), Toast.LENGTH_SHORT).show();
            return;
        }

        // startTime endTime
        if (data.getTask().getStartTime() > data.getTask().getEndTime()) {
            Toast.makeText(context, getString(Strings.add_todo_invalid_tips_start_end_time), Toast.LENGTH_SHORT).show();
            return;
        }

        // priority
        if (data.getTask().getPriority() < Task.PRIORITY_LOW || data.getTask().getPriority() > Task.PRIORITY_URGENT) {
            Toast.makeText(context, getString(Strings.add_todo_invalid_tips_priority), Toast.LENGTH_SHORT).show();
            return;
        }

        setStateAsync(() -> {
            data = getDataFromView();
            try {
                if (data.getTask().getId() != null) {
                    actionResult = LocalTaskDataSource.getInstance(context).update(data.getTask(), data.getTags());
                } else {
                    actionResult = LocalTaskDataSource.getInstance(context).add(data.getTask(), data.getTags());
                }
            } catch (Throwable e) {
                Logger.e(e, e.getMessage());
                actionResult = false;
            }
            action = Action.AddData;
        });
    }

    private void addData() {
        if (!actionResult) {
            Toast.makeText(context, getString(data.getTask().getId() == null ? Strings.add_todo_add_data_error : Strings.edit_todo_add_data_error), Toast.LENGTH_SHORT).show();
            return;
        }

        WidgetNavigator.pop(RouterConfig.name, actionResult);
    }

    private TaskWithTags getDataFromView() {
        Task task = data.getTask();
        task.setTitle(ewTitle.text().toString());
        task.setDescription(ewDesc.text().toString());
        if (task.getStatus() == null) {
            task.setStatus(Task.STATUS_WAITING);
        }

        String tagStr = ewTags.text().toString();
        String[] tags = tagStr.split(",");
        List<Tag> list = new ArrayList<>(tags.length);
        for (String tag : tags) {
            if (TextUtils.isEmpty(tag)) {
                continue;
            }
            list.add(new Tag(tag));
        }

        data.setTags(list);
        return data;
    }

    private void selectDate(boolean start) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(start ? data.getTask().getStartTime() : data.getTask().getEndTime());
        DatePickerDialog dialog = new DatePickerDialog(context,
            (view, year, month, dayOfMonth) -> {
                setState(() -> {
                    if (start) {
                        data.getTask().setStartTime(DateUtils.getDateStartTime(year, month, dayOfMonth));
                    } else {
                        data.getTask().setEndTime(DateUtils.getDateEndTime(year, month, dayOfMonth));
                    }
                });
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
}
