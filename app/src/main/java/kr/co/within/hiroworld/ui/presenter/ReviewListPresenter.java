package kr.co.within.hiroworld.ui.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.twistedequations.mvl.rx.AndroidRxSchedulers;

import java.util.Collections;

import kr.co.within.hiroworld.data.model.ReviewListData;
import kr.co.within.hiroworld.ui.model.ReviewListModel;
import kr.co.within.hiroworld.ui.view.ReviewListContract;
import kr.co.within.hiroworld.ui.view.ReviewListView;
import kr.co.within.hiroworld.util.MyLog;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.firebase.ui.database.ChangeEventListener.EventType.MOVED;
import static kr.co.within.hiroworld.util.Constants.DATABASE_REVIEWS;

/**
 * Created by chogoon on 2017-07-03.
 */

public class ReviewListPresenter implements ReviewListContract.Presenter {

    private final ReviewListView view;
    private final ReviewListModel model;
    private final AndroidRxSchedulers androidSchedulers;
    private final DatabaseReference databaseRef;

    private FirebaseAuth.AuthStateListener authListener;
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    public ReviewListPresenter(ReviewListView view, ReviewListModel model, AndroidRxSchedulers androidSchedulers) {
        this.view = view;
        this.model = model;
        this.androidSchedulers = androidSchedulers;
        this.databaseRef = FirebaseDatabase.getInstance().getReference().child(DATABASE_REVIEWS);
    }

    @Override
    public void subscribe() {
        compositeSubscription.add(writeClick());
//        compositeSubscription.add(loadReviewList());
        compositeSubscription.add(reviewEventObservable());
    }

    @Override
    public Subscription writeClick(){
        return view.writeButton()
                .doOnNext(aVoid -> view.showLoading(true))
                .subscribe(itemView -> {
                    view.showLoading(false);
                    model.startActivity();
                });
    }

    @Override
    public Subscription loadReviewList() {
//        return model.loadReviewsByBilding().subscribe(view :: showReviews);
        return model.getSavedReviewListing()
                .switchIfEmpty(model.loadReviewsByid()
                    .subscribeOn(androidSchedulers.network())
                    .observeOn(androidSchedulers.mainThread())
                        .filter(reviewDataList -> {
                            Collections.sort(reviewDataList);
                            return true;
                        })
                    .map(reviewDataList -> new ReviewListData(reviewDataList))
                    .map(model::saveReviewListing))
                .observeOn(androidSchedulers.io())
                .map(reviewListData -> reviewListData.data)
                .observeOn(androidSchedulers.mainThread())
                .filter(__ ->{
                        compositeSubscription.add(reviewEventObservable());
                        return true;
                })
                .subscribe(view::showReviews);
    }

    @Override
    public Subscription reviewEventObservable() {
        return model.eventObservable().subscribe(reviewDataRxFirebaseChildEvent -> {
            MyLog.e(reviewDataRxFirebaseChildEvent.getEventType() + " / " + reviewDataRxFirebaseChildEvent.getValue().content);
            switch (reviewDataRxFirebaseChildEvent.getEventType()){
                case ADDED:
                    view.addData(reviewDataRxFirebaseChildEvent.getValue());
                    break;
                case CHANGED:
                    view.swapData(reviewDataRxFirebaseChildEvent.getValue());
                    break;
                case MOVED:
                    view.showToast(MOVED.name());
                    break;
                case REMOVED:
                    view.removeData(reviewDataRxFirebaseChildEvent.getValue());
                    break;
            }
        });
    }

    @Override
    public void unsubscribe() {
        compositeSubscription.clear();
    }


}
