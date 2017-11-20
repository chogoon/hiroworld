package kr.co.within.hiroworld.ui.view;

import android.view.View;
import android.view.ViewOutlineProvider;

/**
 * Created by within2 on 2017-09-06.
 */
class ViewUtilsLollipop {

    static void setBoundsViewOutlineProvider(View view) {
        view.setOutlineProvider(ViewOutlineProvider.BOUNDS);
    }

}
