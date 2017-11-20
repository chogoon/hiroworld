package kr.co.within.hiroworld.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.within.hiroworld.R;
import kr.co.within.hiroworld.ui.BaseActivity;
import kr.co.within.hiroworld.ui.view.NewCollapsingToolbarLayout;

/**
 * Created by chogoon on 2017-07-03.
 */

public class CollapsingActivity extends BaseActivity{

    public static void start(Context context){
        Intent intent = new Intent(context, CollapsingActivity.class);
        context.startActivity(intent);
    }

//    @BindView(R.id.toolbar)
//    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    NewCollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void setupActivityComponent() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing);
        ButterKnife.bind(this);

//        setSupportActionBar(toolbar);


        collapsingToolbarLayout.setTitle("Details");
//        ImageView imageView = new ImageView(this);
//        imageView.setImageResource(R.drawable.ic_accessibility_black_24px);
//        collapsingToolbarLayout.addView(imageView);
        collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER_HORIZONTAL);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
