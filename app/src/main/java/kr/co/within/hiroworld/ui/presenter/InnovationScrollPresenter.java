package kr.co.within.hiroworld.ui.presenter;

import com.twistedequations.mvl.rx.AndroidRxSchedulers;

import kr.co.within.hiroworld.ui.model.InnovationScrollModel;
import kr.co.within.hiroworld.ui.view.InnovationScrollContract;
import kr.co.within.hiroworld.ui.view.InnovationScrollView;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chogoon on 2017-07-03.
 */

public class InnovationScrollPresenter implements InnovationScrollContract.Presenter {

    private final InnovationScrollView view;
    private final InnovationScrollModel model;
    private final AndroidRxSchedulers androidSchedulers;

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    public InnovationScrollPresenter(InnovationScrollView view, InnovationScrollModel model, AndroidRxSchedulers androidSchedulers) {
        this.view = view;
        this.model = model;
        this.androidSchedulers = androidSchedulers;
    }

    @Override
    public void subscribe() {
    }

    @Override
    public Subscription sendReview() {
        return null;
    }

    @Override
    public Subscription receiveMessage() {
        return null;
    }


    @Override
    public void unsubscribe() {
        compositeSubscription.clear();
    }

    @Override
    public Subscription sendEditRealTime() {
        return null;
    }
}
