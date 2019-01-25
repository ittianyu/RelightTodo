package android.support.v4.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import java.lang.reflect.Field;

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
            try {
                Field f = ViewPager.class.getDeclaredField("mCurItem");
                f.setAccessible(true);
                f.set(this, item);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            getAdapter().notifyDataSetChanged();
        }
        super.setCurrentItem(item, smoothScroll);
    }
}
