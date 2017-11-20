package kr.co.within.hiroworld.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import javax.inject.Inject;

import kr.co.within.hiroworld.app.MyApplication;
import kr.co.within.hiroworld.dagger.component.DaggerSplashComponent;
import kr.co.within.hiroworld.dagger.module.SplashModule;
import kr.co.within.hiroworld.data.model.BuildingData;
import kr.co.within.hiroworld.data.network.ApiService;
import kr.co.within.hiroworld.ui.BaseActivity;
import kr.co.within.hiroworld.ui.presenter.SplashPresenter;
import kr.co.within.hiroworld.ui.view.SplashView;
import kr.co.within.hiroworld.util.MyLog;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static kr.co.within.hiroworld.ui.model.SplashModel.REQUEST_SIGN_GOOGLE;
import static kr.co.within.hiroworld.util.Constants.DATABASE_BUILDINGS;

/**
 * Created by chogoon on 2017-07-03.
 */

public class SplashActivity extends BaseActivity{

    @Inject
    SplashView view;

    @Inject
    SplashPresenter presenter;

    @Inject
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(view);
        presenter.subscribe();

    }

    @Override
    protected void setupActivityComponent() {
        DaggerSplashComponent.builder()
                .splashModule(new SplashModule(this))
                .appComponent(MyApplication.get(this).component())
                .build().inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // google
        if(requestCode == REQUEST_SIGN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            presenter.getAuthWithGoogle(result);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.unsubscribe();
        super.onDestroy();
    }

}
