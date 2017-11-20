package kr.co.within.hiroworld.ui.model;

import android.app.Activity;
import android.view.View;

import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;

import java.util.List;

import kr.co.within.hiroworld.data.model.BuildingData;
import kr.co.within.hiroworld.data.model.RecommendData;
import kr.co.within.hiroworld.data.model.ReviewData;
import kr.co.within.hiroworld.data.source.remote.FirebaseService;
import kr.co.within.hiroworld.ui.activity.ReviewWriteActivity;
import kr.co.within.hiroworld.util.MyLog;
import rx.Completable;
import rx.Observable;
import rx.Subscription;

import static kr.co.within.hiroworld.ui.activity.ReviewWriteActivity.INTENT_DATA_BUILDING;

/**
 * Created by chogoon on 2017-07-03.
 */

public class ReviewWriteModel {

    private final Activity activity;
    private final FirebaseService fbService;
    private final BuildingData buildingData;

    public ReviewWriteModel(Activity activity, FirebaseService fbService) {
        this.activity = activity;
        this.fbService = fbService;
        this.buildingData = activity.getIntent().getParcelableExtra(INTENT_DATA_BUILDING);
    }

    public Activity getActivity() {
        return activity;
    }

    public void startActivity(View view) {
        final BuildingData data = (BuildingData) view.getTag();
        MyLog.e(data);
        ReviewWriteActivity.start(activity, data);
    }

    public Completable sendReviews(ReviewData data){
        data.bid = buildingData.id;
        return fbService.sendReviews(data);
    }

    public Observable<RxFirebaseChildEvent<RecommendData>> getItemRecommendObservable(String uid){
        return fbService.getItemRecommendObservable(uid);
    }


}
