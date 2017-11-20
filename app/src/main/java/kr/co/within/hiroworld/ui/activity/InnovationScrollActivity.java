package kr.co.within.hiroworld.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.within.hiroworld.R;
import kr.co.within.hiroworld.app.MyApplication;
import kr.co.within.hiroworld.dagger.component.DaggerInnovationScrollComponent;
import kr.co.within.hiroworld.dagger.component.DaggerReviewWriteComponent;
import kr.co.within.hiroworld.dagger.module.InnovationScrollModule;
import kr.co.within.hiroworld.dagger.module.ReviewWriteModule;
import kr.co.within.hiroworld.ui.BaseActivity;
import kr.co.within.hiroworld.ui.presenter.InnovationScrollPresenter;
import kr.co.within.hiroworld.ui.presenter.ReviewWritePresenter;
import kr.co.within.hiroworld.ui.view.InnovationScrollView;
import kr.co.within.hiroworld.ui.view.ReviewWriteView;

public class InnovationScrollActivity extends BaseActivity{

    public static void start(Context context){
        Intent intent = new Intent(context, InnovationScrollActivity.class);
        context.startActivity(intent);
    }

    @Inject
    InnovationScrollView view;

    @Inject
    InnovationScrollPresenter presenter;


    @Override
    protected void setupActivityComponent() {
        DaggerInnovationScrollComponent.builder()
                .innovationScrollModule(new InnovationScrollModule(this))
                .appComponent(MyApplication.get(this).component())
                .build().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        presenter.subscribe();

    }


    @Override
    protected void onDestroy() {
        presenter.unsubscribe();
        super.onDestroy();
    }

}
