package kr.co.within.hiroworld.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import kr.co.within.hiroworld.R;
import kr.co.within.hiroworld.app.MyApplication;
import kr.co.within.hiroworld.dagger.component.DaggerBuildingListComponent;
import kr.co.within.hiroworld.dagger.module.BuildingListModule;
import kr.co.within.hiroworld.ui.BaseActivity;
import kr.co.within.hiroworld.ui.fragment.BuildingListFragment;
import kr.co.within.hiroworld.ui.presenter.BuildingListPresenter;
import kr.co.within.hiroworld.util.ActivityUtils;
import kr.co.within.hiroworld.util.MyLog;

/**
 * Created by chogoon on 2017-07-03.
 */

public class BuildingListActivity extends BaseActivity{

    public static void start(Context context){
        Intent intent = new Intent(context, BuildingListActivity.class);
        context.startActivity(intent);
    }

    @Inject
    BuildingListPresenter presenter;

    @Override
    protected void setupActivityComponent() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_review);

        BuildingListFragment fragment = (BuildingListFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame_);

        if (fragment == null) {
            MyLog.e("fragment == null");
            fragment = BuildingListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.content_frame_);
        }

        DaggerBuildingListComponent.builder()
                .buildingListModule(new BuildingListModule(this, fragment))
                .appComponent(MyApplication.get(this).component())
                .build().inject(this);

    }


    @Override
    protected void onDestroy() {
//        presenter.unsubscribe();
        super.onDestroy();
    }



}
