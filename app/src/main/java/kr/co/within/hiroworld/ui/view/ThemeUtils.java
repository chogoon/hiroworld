package kr.co.within.hiroworld.ui.view;

/**
 * Created by within2 on 2017-09-06.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.R;

class ThemeUtils {

    private static final int[] APPCOMPAT_CHECK_ATTRS = { R.attr.colorPrimary };

    static void checkAppCompatTheme(Context context) {
        TypedArray a = context.obtainStyledAttributes(APPCOMPAT_CHECK_ATTRS);
        final boolean failed = !a.hasValue(0);
        if (a != null) {
            a.recycle();
        }
        if (failed) {
            throw new IllegalArgumentException("You need to use a Theme.AppCompat theme "
                    + "(or descendant) with the design library.");
        }
    }
}
