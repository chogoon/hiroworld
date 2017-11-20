package kr.co.within.hiroworld.ui.model;

import android.app.Activity;

import com.firebase.ui.auth.ui.User;
import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.twistedequations.rxstate.RxSaveState;

import java.util.List;

import kr.co.within.hiroworld.data.model.BuildingData;
import kr.co.within.hiroworld.data.model.ReviewData;
import kr.co.within.hiroworld.data.model.ReviewListData;
import kr.co.within.hiroworld.data.source.remote.FirebaseService;
import kr.co.within.hiroworld.ui.BaseModel;
import kr.co.within.hiroworld.ui.activity.ReviewWriteActivity;
import rx.Observable;

import static kr.co.within.hiroworld.ui.activity.ReviewWriteActivity.INTENT_DATA_BUILDING;

/**
 * Created by chogoon on 2017-07-03.
 */

public class ReviewListModel implements BaseModel{

    public static final int REQUEST_SIGN_GOOGLE = 1101;
    public static final String REVIEW_LISTING = "REVIEW_LISTING";
    private final Activity activity;
    private final FirebaseService fbService;
    private final BuildingData data;

    public ReviewListModel(Activity activity, FirebaseService fbService) {
        this.activity = activity;
        this.fbService = fbService;
        this.data = activity.getIntent().getParcelableExtra(INTENT_DATA_BUILDING);
    }

    public Activity getActivity() {
        return activity;
    }


    public void startActivity() {
        ReviewWriteActivity.start(activity, data);
    }

    public Observable<RxFirebaseChildEvent<ReviewData>> eventObservable(){
        return fbService.getItemReviewObservable(data.id);
    }

    public Observable<List<ReviewData>> loadReviewsByid(){
        return fbService.loadReviewsByid(data.id);
    }

    public ReviewListData saveReviewListing(ReviewListData reviewListing) {
        RxSaveState.updateSaveState(activity, bundle -> bundle.putParcelable(REVIEW_LISTING, reviewListing));
        return reviewListing;
    }


    public Observable<ReviewListData> getSavedReviewListing() {
        return RxSaveState.getSavedState(activity).map(bundle -> bundle.getParcelable(REVIEW_LISTING));
    }




}
