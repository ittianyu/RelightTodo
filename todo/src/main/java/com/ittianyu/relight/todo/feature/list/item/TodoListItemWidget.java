package com.ittianyu.relight.todo.feature.list.item;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.ittianyu.relight.todo.ResLoader;
import com.ittianyu.relight.todo.common.constants.Colors;
import com.ittianyu.relight.todo.common.constants.Images;
import com.ittianyu.relight.todo.common.constants.Selectors;
import com.ittianyu.relight.todo.common.constants.Sizes;
import com.ittianyu.relight.todo.common.constants.Strings;
import com.ittianyu.relight.todo.common.datasource.entiry.Tag;
import com.ittianyu.relight.todo.common.datasource.entiry.Task;
import com.ittianyu.relight.todo.common.datasource.entiry.TaskWithTags;
import com.ittianyu.relight.todo.common.utils.DateUtils;
import com.ittianyu.relight.utils.DensityUtils;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.BaseLinearWidget;
import com.ittianyu.relight.widget.native_.ImageWidget;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.RelativeWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.native_.material.ChipGroupWidget;
import com.ittianyu.relight.widget.native_.material.ChipWidget;
import com.ittianyu.relight.widget.stateless.StatelessWidget;
import java.util.List;

public class TodoListItemWidget extends StatelessWidget<LinearLayout, LinearWidget> implements ResLoader {

    private ImageWidget iwStatus;
    private TextWidget twTitle;
    private ChipWidget twPriority;
    private TextWidget twDesc;
    private TextWidget twStartTime;
    private TextWidget twEndTime;
    private ChipGroupWidget cgw;
    private TaskWithTags taskWithTags;

    public TodoListItemWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected LinearWidget build(Context context) {
        return new LinearWidget(context, lifecycle,
            renderStatus().padding(Sizes.margin_normal),
            renderContent()
        )
            .widthMatchAndHeightWrap()
            .paddingVertical(Sizes.margin_normal)
            .paddingEnd(Sizes.margin_normal)
            .orientation(BaseLinearWidget.horizontal)
            .gravity(Gravity.CENTER_VERTICAL);
    }

    private ImageWidget renderStatus() {
        iwStatus = new ImageWidget(context, lifecycle);
        return iwStatus;
    }

    private LinearWidget renderContent() {
        return new LinearWidget(context, lifecycle,
            renderTop(),
            renderDesc(),
            renderTime().marginVertical(Sizes.margin_small2),
            renderTags()
        )
            .widthMatchAndHeightWrap()
            .orientation(LinearWidget.vertical);
    }

    private LinearWidget renderTop() {
        twTitle = new TextWidget(context, lifecycle)
            .textColor(getColor(Colors.color_black))
            .textSize(Sizes.text_title6);
        twPriority = new ChipWidget(context, lifecycle)
            .chipMinHeight(DensityUtils.dip2px(context, Sizes.button_height_small));

        return new LinearWidget(context, lifecycle,
            twTitle.weight(1),
            twPriority
        )
            .orientation(LinearWidget.horizontal)
            .widthMatchAndHeightWrap();
    }

    private Widget renderDesc() {
        twDesc = new TextWidget(context, lifecycle)
            .maxLines(3);
        return twDesc;
    }

    private RelativeWidget renderTime() {
        twStartTime = new TextWidget(context, lifecycle)
            .drawableLeft(getDrawable(context, Images.ic_date_start));
        twEndTime = new TextWidget(context, lifecycle)
            .drawableLeft(getDrawable(context, Images.ic_date_end));

        return new RelativeWidget(context, lifecycle,
            twStartTime,
            twEndTime.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
        );
    }

    public TodoListItemWidget data(TaskWithTags taskWithTags) {
        this.taskWithTags = taskWithTags;
        if (taskWithTags == null) {
            return this;
        }
        update();
        return this;
    }

    public TaskWithTags data() {
        return taskWithTags;
    }

    private ColorStateList getStatusTint(Short status) {
        return ColorStateList.valueOf(getColor(Colors.color_common_gray1));
    }

    public TodoListItemWidget onCheck(OnClickListener onCheck) {
        iwStatus.onClick(onCheck);
        return this;
    }

    public TodoListItemWidget onClick(OnClickListener onCheck) {
        widget.onClick(onCheck);
        return this;
    }

    private Integer getPriorityColor(Short priority) {
        String colorStr = null;
        switch (priority) {
            case Task.PRIORITY_LOW:
                colorStr = Colors.color_common_gray1;
                break;
             case Task.PRIORITY_NORMAL:
                colorStr = Colors.color_black;
                break;
             case Task.PRIORITY_HIGH:
             case Task.PRIORITY_URGENT:
                colorStr = Colors.color_white;
                break;
            default:
                colorStr = Colors.color_black;
                break;
        }
        return getColor(colorStr);
    }

    private ColorStateList getPriorityBackground(Short priority) {
        ColorStateList list = null;
        switch (priority) {
             case Task.PRIORITY_HIGH:
                 list = Selectors.pink_color_selector;
                break;
             case Task.PRIORITY_URGENT:
                 list = Selectors.red_color_selector;
                break;
            default:
                list = twPriority.defaultBackgroundColor();
                break;
        }
        return list;
    }

    private String getStatusImage(int status) {
        switch (status) {
            case Task.STATUS_WAITING:
                return Images.ic_status_wait;
            case Task.STATUS_DOING:
                return Images.ic_status_doing;
            case Task.STATUS_COMPLETED:
                return Images.ic_status_completed;
        }
        return null;
    }

    private String getPriority(int priority) {
        switch (priority) {
            case Task.PRIORITY_LOW:
                return getString(Strings.priority_low);
            case Task.PRIORITY_NORMAL:
                return getString(Strings.priority_normal);
            case Task.PRIORITY_HIGH:
                return getString(Strings.priority_high);
            case Task.PRIORITY_URGENT:
                return getString(Strings.priority_urgent);
            default:
                return "";
        }
    }

    private ChipGroupWidget renderTags() {
        cgw = new ChipGroupWidget(context, lifecycle)
            .widthMatchAndHeightWrap();
        return cgw;
    }

    private ChipWidget renderTag(Tag tag) {
        return new ChipWidget(context, lifecycle)
            .text(tag.getName())
            .chipMinHeight(DensityUtils.dip2px(context, Sizes.button_height_small))
            .chipBackgroundColor(Selectors.primary_color_selector)
            .textColor(Color.WHITE);
    }

    @Override
    public void update() {
        super.update();
        Task task = taskWithTags.getTask();
        List<Tag> tags = taskWithTags.getTags();

        iwStatus.imageDrawable(getDrawable(context, getStatusImage(task.getStatus())))
            .imageTintList(getStatusTint(task.getStatus()));
        twTitle.text(task.getTitle());
        twDesc.text(task.getDescription())
            .visibility(!TextUtils.isEmpty(task.getDescription()));
        twPriority.text(getPriority(task.getPriority()))
            .textColor(getPriorityColor(task.getPriority()))
            .chipBackgroundColor(getPriorityBackground(task.getPriority()));
        twStartTime.text(DateUtils.formatDate(task.getStartTime()));
        twEndTime.text(DateUtils.formatDate(task.getEndTime()));

        cgw.removeAllChildren();
        for (Tag tag : tags) {
            cgw.addChild(renderTag(tag));
        }
        cgw.visibility(cgw.childrenSize() > 0);
    }
}
