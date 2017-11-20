package kr.co.within.hiroworld.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.within.hiroworld.R;
import kr.co.within.hiroworld.ui.BaseActivity;
import kr.co.within.hiroworld.ui.view.SwitchLottieAnimationView;
import kr.co.within.hiroworld.util.MyLog;

/**
 * Created by chogoon on 2017-07-03.
 */

public class SvgVectorDrawableActivity extends BaseActivity{

    public static void start(Context context){
        Intent intent = new Intent(context, SvgVectorDrawableActivity.class);
        context.startActivity(intent);
    }

//    @BindView(R.id.svg_image1_)
//    ImageView svgImage1;
//
//    @BindView(R.id.svg_image3_)
//    ImageView svgImage3;
//
//    @BindView(R.id.lottie1_)
//    LottieAnimationView lottie1;
//
//    @BindView(R.id.lottie2_)
//    LottieAnimationView lottie2;

    @BindView(R.id.lottie4_)
    SwitchLottieAnimationView lottie4;

    @Override
    protected void setupActivityComponent() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        ButterKnife.bind(this);
//        if (svgImage3 != null) {
//            VectorDrawableCompat vcAccept = VectorDrawableCompat.create(getResources(), R.drawable.ic_accessibility_black_24px, getTheme());
//            VectorDrawableCompat vcAcceptWhite = VectorDrawableCompat.create(getResources(), R.drawable.ic_accessibility_black_24px_, getTheme());
//
//            StateListDrawable stateList = new StateListDrawable();
//            stateList.addState(new int[]{android.R.attr.state_focused, -android.R.attr.state_pressed}, vcAccept);
//            stateList.addState(new int[]{android.R.attr.state_focused, android.R.attr.state_pressed}, vcAcceptWhite);
//            stateList.addState(new int[]{-android.R.attr.state_focused, android.R.attr.state_pressed}, vcAcceptWhite);
//            stateList.addState(new int[]{}, vcAccept);
//
//            svgImage3.setImageDrawable(stateList);
//            svgImage3.setBackgroundResource(R.drawable.btn_svg_22);
//
//
//
//        }
        lottie4.setAnimation("switch.json");
        lottie4.setScale(5f);
        lottie4.setOnOffset(0.6f);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
