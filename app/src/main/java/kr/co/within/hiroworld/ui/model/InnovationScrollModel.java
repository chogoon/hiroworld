package kr.co.within.hiroworld.ui.model;

import android.app.Activity;
import android.view.View;

import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;

import kr.co.within.hiroworld.data.model.BuildingData;
import kr.co.within.hiroworld.data.model.RecommendData;
import kr.co.within.hiroworld.data.model.ReviewData;
import kr.co.within.hiroworld.data.source.remote.FirebaseService;
import kr.co.within.hiroworld.ui.activity.ReviewWriteActivity;
import kr.co.within.hiroworld.util.MyLog;
import rx.Completable;
import rx.Observable;

import static kr.co.within.hiroworld.ui.activity.ReviewWriteActivity.INTENT_DATA_BUILDING;

/**
 * Created by chogoon on 2017-07-03.
 */

public class InnovationScrollModel {

    private final Activity activity;

    public InnovationScrollModel(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void startActivity(View view) {
//        final BuildingData data = (BuildingData) view.getTag();
//        MyLog.e(data);
//        ReviewWriteActivity.start(activity, data);
    }


}
