package kr.co.within.hiroworld.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import kr.co.within.hiroworld.app.MyApplication;
import kr.co.within.hiroworld.dagger.component.DaggerReviewListComponent;
import kr.co.within.hiroworld.dagger.module.ReviewListModule;
import kr.co.within.hiroworld.data.model.BuildingData;
import kr.co.within.hiroworld.ui.BaseActivity;
import kr.co.within.hiroworld.ui.presenter.ReviewListPresenter;
import kr.co.within.hiroworld.ui.view.ReviewListView;

import static kr.co.within.hiroworld.ui.activity.ReviewWriteActivity.INTENT_DATA_BUILDING;

/**
 * Created by chogoon on 2017-07-03.
 */

public class ReviewListActivity extends BaseActivity{

    public static void start(Context context, BuildingData data){
        Intent intent = new Intent(context, ReviewListActivity.class);
        intent.putExtra(INTENT_DATA_BUILDING, data);
        context.startActivity(intent);
    }

    @Inject
    ReviewListView view;

    @Inject
    ReviewListPresenter presenter;

    @Override
    protected void setupActivityComponent() {
        DaggerReviewListComponent.builder()
                .reviewListModule(new ReviewListModule(this))
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
