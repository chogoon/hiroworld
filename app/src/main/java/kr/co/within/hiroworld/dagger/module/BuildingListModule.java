package kr.co.within.hiroworld.dagger.module;

import android.app.Activity;

import com.squareup.picasso.Picasso;
import com.twistedequations.mvl.rx.AndroidRxSchedulers;

import dagger.Module;
import dagger.Provides;
import kr.co.within.hiroworld.dagger.WatchReviewScope;
import kr.co.within.hiroworld.data.network.ApiService;
import kr.co.within.hiroworld.data.source.remote.FirebaseService;
import kr.co.within.hiroworld.ui.model.BuildingListModel;
import kr.co.within.hiroworld.ui.presenter.BuildingListPresenter;
import kr.co.within.hiroworld.ui.view.BuildingListContract;

/**
 * Created by chogoon on 2017-05-31.
 */
@Module
public class BuildingListModule {

    private final Activity activity;
    private final BuildingListContract.View view;

    public BuildingListModule(Activity activity, BuildingListContract.View view) {
        this.activity = activity;
        this.view = view;
    }


    @Provides
    @WatchReviewScope
    public BuildingListModel model(FirebaseService fbService, ApiService apiService){
        return new BuildingListModel(activity, fbService, apiService);
    }

    @Provides
    @WatchReviewScope
    public BuildingListPresenter presenter(BuildingListModel model, Picasso picasso, AndroidRxSchedulers androidSchedulers){
        return new BuildingListPresenter(model, view, picasso, androidSchedulers);
    }


}
