package android.support.v4.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class QuickScrollViewPager extends ViewPager {

    public QuickScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public QuickScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (Math.abs(getCurrentItem() - item) > 1) {
            this.mCurItem = item;
            getAdapter().notifyDataSetChanged();
        }
        super.setCurrentItem(item, smoothScroll);
    }
}
