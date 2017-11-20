package kr.co.within.hiroworld.ui.view;

import java.util.List;

import kr.co.within.hiroworld.ui.BasePresenter;
import kr.co.within.hiroworld.ui.BaseView;
import rx.Subscription;

/**
 * Created by chogoon on 2017-07-06.
 */

public interface InnovationScrollContract {

    interface View extends BaseView<Presenter> {

        void showLoading(boolean flag);

        void setMessages(List list);

        void swapData(Object item);

        void removeData(Object item);

        void showToast(String message);

    }

    interface Presenter extends BasePresenter {

        Subscription sendEditRealTime();

        Subscription sendReview();

        Subscription receiveMessage();

    }
}
