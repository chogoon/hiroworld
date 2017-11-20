package kr.co.within.hiroworld.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

import kr.co.within.hiroworld.util.MyLog;

/**
 * Created by chogoon on 2017-09-08.
 */

public class SwitchLottieAnimationView extends LottieAnimationView {

    private OnClickListener onClickListener;
    private final float START_OFFSET = 0.0f;
    private final float END_OFFSET = 1.0f;
    private float onOffset = 0.5f;

    public SwitchLottieAnimationView(Context context) {
        this(context, null);
    }

    public SwitchLottieAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        MyLog.e("SwitchLottieAnimationView2");
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.e("onClick");
                if(!view.isSelected()){
                    ((LottieAnimationView)view).playAnimation(START_OFFSET, onOffset);
                }else{
                    ((LottieAnimationView)view).playAnimation(onOffset, END_OFFSET);
                }
                view.setSelected(!view.isSelected());
                if(onClickListener != null) onClickListener.onClick(view);
            }
        });
    }

    public void setOnOffset(float onOffset){
        this.onOffset = onOffset;
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
