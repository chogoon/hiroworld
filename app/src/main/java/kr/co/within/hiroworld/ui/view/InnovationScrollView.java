package kr.co.within.hiroworld.ui.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.within.hiroworld.R;
import kr.co.within.hiroworld.data.model.RecommendData;
import kr.co.within.hiroworld.util.MyLog;
import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.IOverScrollUpdateListener;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import rx.Observable;

import static me.everything.android.ui.overscroll.IOverScrollState.STATE_BOUNCE_BACK;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_START_SIDE;

/**
 * Created by chogoon on 2017-07-03.
 */

@SuppressLint("ViewConstructor")
public class InnovationScrollView extends FrameLayout implements InnovationScrollContract.View {

    @BindView(R.id.nestedScrollView_)
    NestedScrollView nestedScrollView;

    @BindView(R.id.container_)
    LinearLayout container;

    @BindView(R.id.scrollView_)
    ScrollView scrollView;

    @BindView(R.id.hide_)
    View hide;

    private final ProgressDialog progressDialog = new ProgressDialog(getContext());

    private IOverScrollDecor mVertOverScrollEffect;


    public InnovationScrollView(Activity activity) {
        super(activity);
        inflate(getContext(), R.layout.activity_innovation_scroll, this);
        ButterKnife.bind(this);
        progressDialog.setMessage("Loading...");
        mVertOverScrollEffect = OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        mVertOverScrollEffect.setOverScrollUpdateListener((decor, state, offset) -> {
            MyLog.e(state + " / " + String.valueOf((int) offset));
            MyLog.e("getScrollY : " + String.valueOf(nestedScrollView.getScrollY()));
            if (state == STATE_DRAG_START_SIDE) {
                if (nestedScrollView.getScrollY() <= 0 && offset >= 0) {
                    scrollView.getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    scrollView.getParent().requestDisallowInterceptTouchEvent(false);
                }
            }
//            else if(state == STATE_BOUNCE_BACK && offset == 0) {
//                scrollView.getParent().requestDisallowInterceptTouchEvent(true);
//            }
            else {
                scrollView.getParent().requestDisallowInterceptTouchEvent(false);
            }
            hide.setTranslationY(offset);
        });
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                MyLog.d( "scrollX : " + scrollX +  " / " + scrollY +  " / " + oldScrollX +  " / " + oldScrollY);
                if(scrollY <= 0){
                    scrollView.getParent().requestDisallowInterceptTouchEvent(true);
                }else{
                    scrollView.getParent().requestDisallowInterceptTouchEvent(false);
                }
            }
        });
    }

    public void setToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showLoading(boolean flag) {
        MyLog.e(flag);
        if (flag) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void setPresenter(InnovationScrollContract.Presenter presenter) {

    }

    @Override
    public void setMessages(List list) {

    }

    @Override
    public void swapData(Object item) {

    }

    @Override
    public void removeData(Object item) {

    }

    @Override
    public void showToast(String message) {

    }
}
