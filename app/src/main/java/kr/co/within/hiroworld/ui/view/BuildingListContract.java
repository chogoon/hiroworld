package kr.co.within.hiroworld.ui.view;

import android.support.annotation.NonNull;
import android.view.View;

import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.util.List;

import kr.co.within.hiroworld.ui.BasePresenter;
import kr.co.within.hiroworld.ui.BaseView;
import rx.Observable;
import rx.Subscription;

/**
 * Created by chogoon on 2017-07-06.
 */

public interface BuildingListContract {

    interface View extends BaseView<Presenter> {

        void showLoading(boolean flag);

        void showBuildings(List list);

        void addBuilding(Object item);

        void swapBuildingData(Object item);

        void removeBuildingData(Object item);

        void showToast(String message);

        Observable<android.view.View> getItemViewClickObservable();

    }

    interface Presenter extends BasePresenter {

        Subscription addNewBuilding();

        Subscription loadBuilding();

        Picasso getPicasso();

        void result(int requestCode, int resultCode);

        void addNewTask();

        void activateTask(@NonNull Task activeTask);

        void clearCompletedTasks();

    }
}
