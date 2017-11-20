package kr.co.within.hiroworld.ui.view;

import java.util.List;

import kr.co.within.hiroworld.ui.BasePresenter;
import kr.co.within.hiroworld.ui.BaseView;
import rx.Observable;
import rx.Subscription;

/**
 * Created by chogoon on 2017-07-06.
 */

public interface ReviewListContract {

    interface View extends BaseView<Presenter> {

        void showToast(String message);

        void showLoading(boolean flag);

        void showReviews(List items);

        void addData(Object item);

        void swapData(Object item);

        void removeData(Object item);

        Observable writeButton();

    }

    interface Presenter extends BasePresenter {

        Subscription loadReviewList();

        Subscription reviewEventObservable();

        Subscription writeClick();

    }
}
