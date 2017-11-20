package kr.co.within.hiroworld.ui.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.twistedequations.mvl.rx.AndroidRxSchedulers;

import org.joda.time.DateTime;

import kr.co.within.hiroworld.R;
import kr.co.within.hiroworld.data.model.ReviewData;
import kr.co.within.hiroworld.ui.model.ReviewWriteModel;
import kr.co.within.hiroworld.ui.view.ReviewWriteContract;
import kr.co.within.hiroworld.ui.view.ReviewWriteView;
import kr.co.within.hiroworld.util.MyLog;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static kr.co.within.hiroworld.util.Constants.DATABASE_REALTIME_TEXT;

/**
 * Created by chogoon on 2017-07-03.
 */

public class ReviewWritePresenter implements ReviewWriteContract.Presenter {

    private final ReviewWriteView view;
    private final ReviewWriteModel model;
    private final AndroidRxSchedulers androidSchedulers;
    private final FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private final DatabaseReference databaseRef;

    private FirebaseAuth.AuthStateListener authListener;
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    public ReviewWritePresenter(ReviewWriteView view, ReviewWriteModel model, AndroidRxSchedulers androidSchedulers) {
        this.view = view;
        this.model = model;
        this.androidSchedulers = androidSchedulers;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseUser = firebaseAuth.getCurrentUser();
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void subscribe() {
        compositeSubscription.add(receiveRecommendItem());
        compositeSubscription.add(sendEditRealTime());
        compositeSubscription.add(sendButton());
    }

    @Override
    public Subscription sendReview() {
        return null;
    }

    @Override
    public Subscription receiveMessage() {
        return null;
    }

    public Subscription sendButton(){
        return view.sendButton()
                .doOnNext(aVoid -> view.showLoading(true))
                .observeOn(androidSchedulers.io())
                .map(aVoid -> new ReviewData(firebaseUser.getUid(), view.getReviewEdit() , DateTime.now().getMillis()))
                .map(reviewData -> model.sendReviews(reviewData).toObservable().subscribe())
                .observeOn(androidSchedulers.mainThread())
                .doOnNext(o -> view.showLoading(false))
                .subscribe(o -> {
                    view.setReviewEdit("");
                    view.setToast(model.getActivity().getString(R.string.completed));
                    model.getActivity().finish();
                });
    }

    public Subscription sendEditRealTime(){
        return view.sendEditRealTime()
                .observeOn(androidSchedulers.io())
                .subscribe(text -> databaseRef.child(DATABASE_REALTIME_TEXT).child(firebaseUser.getUid()).setValue(text));
    }

    public Subscription receiveRecommendItem(){
        return model.getItemRecommendObservable(firebaseUser.getUid()).subscribe(recommendDataRxFirebaseChildEvent -> {
            MyLog.e(recommendDataRxFirebaseChildEvent.getEventType() + " / " + recommendDataRxFirebaseChildEvent.getValue().toString());
            switch (recommendDataRxFirebaseChildEvent.getEventType()){
                case ADDED:
                    view.setRecomendItem(recommendDataRxFirebaseChildEvent.getValue());
                    break;
                case CHANGED:
                    view.setRecomendItem(recommendDataRxFirebaseChildEvent.getValue());
                    break;
                case MOVED:
                    break;
                case REMOVED:
                    view.setRecomendItem(null);
                    break;
            }
        });
    }

    @Override
    public void unsubscribe() {
        compositeSubscription.clear();
    }


}
