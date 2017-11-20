package kr.co.within.hiroworld.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class MyToast {

    public static final String GRAVITY_BOTTOM = "bottom";
    public static final String GRAVITY_CENTER = "center";

    private static MyToast _instance;
    private Toast mToast;

    @SuppressLint("ShowToast")
    private MyToast(Context context) {
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    synchronized private static MyToast getInstance(Context context) {
        if (_instance == null) {
            _instance = new MyToast(context);
        }
        return _instance;
    }

    public static final void show(Context context, int resId) {
        getInstance(context).cancel();
        getInstance(context).getToast(resId).show();
    }

    public static final void show(Context context, int resId, int time) {
        getInstance(context).cancel();
        getInstance(context).getToast(resId, time).show();
    }

    public static final void show(Context context, CharSequence s) {
        getInstance(context).cancel();
        getInstance(context).getToast(s).show();
    }

    public static final void show(Context context, CharSequence s, int time) {
        getInstance(context).cancel();
        getInstance(context).getToast(s, time).show();
    }

    public static final void show(Context context, View container, int resId, String gravity) {
        getInstance(context).cancel();
        getInstance(context).getToast().setView(container);
        getInstance(context).setGravity(gravity).setText(resId);
        getInstance(context).getToast().show();
    }

    public static final void show(Context context, int resId, String gravity) {
        getInstance(context).cancel();
        getInstance(context).setGravity(gravity).setText(resId);
        getInstance(context).getToast().show();
    }

    public static final void show(Context context, CharSequence s, String gravity) {
        getInstance(context).cancel();
        getInstance(context).setGravity(gravity).setText(s);
        getInstance(context).getToast().show();
    }

    private Toast getToast(int resId) {
        mToast.setText(resId);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        return mToast;
    }

    private Toast getToast(int resId, int time) {
        mToast.setText(resId);
        mToast.setDuration(time);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        return mToast;
    }

    @SuppressWarnings("unused")
    private Toast getToast() {
        return mToast;
    }

    private Toast getToast(CharSequence s) {
        mToast.setText(s);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        return mToast;
    }

    private Toast getToast(CharSequence s, int time) {
        mToast.setText(s);
        mToast.setDuration(time);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        return mToast;
    }

    private Toast setGravity(String gravity) {
        if (gravity.equals(GRAVITY_CENTER)) {
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else if (gravity.equals(GRAVITY_BOTTOM)) {
            mToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 90);
        }
        return mToast;
    }

    private void cancel() {
        // mToast.cancel();
    }

}