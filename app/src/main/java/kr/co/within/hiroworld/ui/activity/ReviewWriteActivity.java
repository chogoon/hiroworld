package kr.co.within.hiroworld.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import java.util.ArrayList;

import javax.inject.Inject;

import kr.co.within.hiroworld.app.MyApplication;
import kr.co.within.hiroworld.dagger.component.DaggerReviewWriteComponent;
import kr.co.within.hiroworld.dagger.component.DaggerSplashComponent;
import kr.co.within.hiroworld.dagger.module.ReviewWriteModule;
import kr.co.within.hiroworld.dagger.module.SplashModule;
import kr.co.within.hiroworld.data.model.BuildingData;
import kr.co.within.hiroworld.ui.BaseActivity;
import kr.co.within.hiroworld.ui.presenter.ReviewWritePresenter;
import kr.co.within.hiroworld.ui.presenter.SplashPresenter;
import kr.co.within.hiroworld.ui.view.ReviewWriteView;

import static kr.co.within.hiroworld.ui.model.SplashModel.REQUEST_SIGN_GOOGLE;

/**
 * Created by chogoon on 2017-07-03.
 */

public class ReviewWriteActivity extends BaseActivity{

    public static final String INTENT_DATA_BUILDING = "INTENT_DATA_BUILDING";

    public static void start(Context context, BuildingData data){
        Intent intent = new Intent(context, ReviewWriteActivity.class);
        intent.putExtra(INTENT_DATA_BUILDING, data);
        context.startActivity(intent);
    }

    @Inject
    ReviewWriteView view;

    @Inject
    ReviewWritePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        presenter.subscribe();
    }

    @Override
    protected void setupActivityComponent() {
        DaggerReviewWriteComponent.builder()
                .reviewWriteModule(new ReviewWriteModule(this))
                .appComponent(MyApplication.get(this).component())
                .build().inject(this);
    }

    @Override
    protected void onDestroy() {
        presenter.unsubscribe();
        super.onDestroy();
    }

}
