package kr.co.within.hiroworld.ui.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kelvinapps.rxfirebase.DataSnapshotMapper;
import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.twistedequations.mvl.rx.AndroidRxSchedulers;

import kr.co.within.hiroworld.data.model.BuildingData;
import kr.co.within.hiroworld.data.model.ReviewData;
import kr.co.within.hiroworld.ui.model.BuildingListModel;
import kr.co.within.hiroworld.ui.view.BuildingListContract;
import kr.co.within.hiroworld.util.MyLog;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;
import static kr.co.within.hiroworld.util.Constants.DATABASE_BUILDINGS;
import static kr.co.within.hiroworld.util.Constants.DATABASE_REVIEWS;

/**
 * Created by chogoon on 2017-07-03.
 */

public class BuildingListPresenter implements BuildingListContract.Presenter {

    private final BuildingListModel model;
    private final Picasso picasso;
    private final FirebaseAuth firebaseAuth;
    private final DatabaseReference databaseRef;
    private FirebaseUser firebaseUser;
    private BuildingListContract.View view;

    private FirebaseAuth.AuthStateListener authListener;
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    private ChildEventListener buildingListRef;
    private final AndroidRxSchedulers androidSchedulers;

    public BuildingListPresenter(@NonNull BuildingListModel model, @NonNull BuildingListContract.View view, Picasso picasso, AndroidRxSchedulers androidSchedulers) {
        this.model = checkNotNull(model, "BuildingListModel cannot be null!");
        this.picasso = checkNotNull(picasso, "picasso cannot be null!");
        this.view = checkNotNull(view, "BuildingListContract cannot be null!");
        this.view.setPresenter(this);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
        this.androidSchedulers = androidSchedulers;
    }

    @Override
    public Picasso getPicasso() {
        return picasso;
    }

    @Override
    public void subscribe() {
//        loadBuilding();
        compositeSubscription.add(loadBuilding());
        compositeSubscription.add(itemClick());
    }

    @Override
    public void unsubscribe() {
        compositeSubscription.clear();
        if (buildingListRef != null) {
            databaseRef.removeEventListener(buildingListRef);
        }
    }

    private Subscription itemClick(){
        return view.getItemViewClickObservable()
                .doOnNext(itemView -> view.showLoading(true))
                .subscribe(itemView -> {
                    view.showLoading(false);
                    model.startActivity(itemView);
                });
    }

    @Override
    public Subscription addNewBuilding() {
        MyLog.e("addNewBuilding");
        return Observable.just("테스트")
                .doOnNext(name -> view.showLoading(true))
                .observeOn(Schedulers.io())
                .switchMap(name -> model.getSearchBuildingList(name))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(buildingListData -> {
                    MyLog.e("result" + buildingListData.toString());
                    for (BuildingData data : buildingListData.data.result) {
                        databaseRef.child(DATABASE_BUILDINGS).child(String.valueOf(data.id)).setValue(data);
                    }
                    view.showBuildings(buildingListData.data.result);
                    view.showLoading(false);
                },
                        Throwable::printStackTrace
                );
    }

    private Observable<RxFirebaseChildEvent<BuildingData>> loadBuildings(){
        return RxFirebaseDatabase.observeChildEvent(databaseRef.child(DATABASE_BUILDINGS), BuildingData.class);
//                .doOnNext(notification -> view.showLoading(true))
//                .subscribe(buildingDataRxFirebaseChildEvent -> {
//                    final BuildingData parent = buildingDataRxFirebaseChildEvent.getValue();
//
//
//                }));
//                .flatMap(buildingDataRxFirebaseChildEvent -> RxFirebaseDatabase.observeChildEvent(databaseRef.child(DATABASE_REVIEWS).child(String.valueOf(buildingDataRxFirebaseChildEvent.getValue().id)), ReviewData.class))
//                .filter(reviewDataRxFirebaseChildEvent::)
//                .flatMap(buildingData ->
//                        .map(reviewData -> buildingData.reviews.addAll(reviewData.reviews))
//                )
//                .subscribe()
//
//                .map(dataSnapshotRxFirebaseChildEvent -> {
//                    final BuildingData parent = ;
//                    RxFirebaseDatabase.observeSingleValueEvent(databaseRef.child(DATABASE_REVIEWS).child(String.valueOf(parent.id)), ReviewData.class).map(
//                            reviewData -> parent.reviews = reviewData.reviews;
//                    )
//                })
//                .observeOn(androidSchedulers.io())
//                .switchMap(dataSnapshotRxFirebaseChildEvent -> {
//                    final BuildingData parent = dataSnapshotRxFirebaseChildEvent.getChildrenCount
//
//                });

    }

     @Override
    public Subscription loadBuilding() {
        MyLog.e("loadBuilding");
        return loadBuildings().subscribe(buildingDataRxFirebaseChildEvent -> {
            final BuildingData parent = buildingDataRxFirebaseChildEvent.getValue();
            switch (buildingDataRxFirebaseChildEvent.getEventType()){
                        case ADDED:
                            RxFirebaseDatabase.observeSingleValueEvent(databaseRef.child(DATABASE_REVIEWS)
                                    .child(String.valueOf(buildingDataRxFirebaseChildEvent.getValue().id)), DataSnapshotMapper.listOf(ReviewData.class))
                                    .subscribe(reviewDatas -> {
                                        parent.reviews.addAll(reviewDatas);
                                        view.addBuilding(parent);
                                    });

                            break;
                        case CHANGED:
                            RxFirebaseDatabase.observeSingleValueEvent(databaseRef.child(DATABASE_REVIEWS).child(String.valueOf(buildingDataRxFirebaseChildEvent.getValue().id)), DataSnapshotMapper.listOf(ReviewData.class))
                                    .subscribe(reviewDatas -> {
                                        parent.reviews.addAll(reviewDatas);
                                        view.swapBuildingData(parent);
                                    });
                            break;
                        case MOVED:
                            break;
                        case REMOVED:
                            view.removeBuildingData(parent);
                            break;

                    }

        });
//        return Observable.just(null)
//                .flatMap(aVoid -> loadBuildings())
//                .doOnError(Throwable::printStackTrace)
//                .subscribe(view::showBuildings); // display data
////
//        final PublishSubject<List<BuildingData>> listPublishSubject = PublishSubject.create();
//
//        final Subscription postSub = listPublishSubject.map(buildingDatas -> buildingDatas.get(0))
//                .map(buildingData -> buildingData.data.children.get(0).data)
//                .onErrorResumeNext(throwable -> Observable.empty())
//                .observeOn(androidSchedulers.mainThread())
//                .subscribe(defaultPostView::setRedditItem);
//
//        return Observable.just(null)
//                .flatMap(aVoid -> loadRedditItems())
//
//        return RxFirebaseDatabase.observeChildEvent(databaseRef.child(DATABASE_BUILDINGS), BuildingData.class)
//                .doOnNext(name -> view.showLoading(true))
//                .flatMap(buildingDataRxFirebaseChildEvent -> {Observable})
//                .subscribe(dataSnapshotRxFirebaseChildEvent -> {
//                    final BuildingData parent = dataSnapshotRxFirebaseChildEvent.getValue();
//                    switch (dataSnapshotRxFirebaseChildEvent.getEventType()){
//                        case ADDED:
//                            view.addBuilding(parent);
//
//                            break;
//                        case CHANGED:
//                            view.swapData(parent);
//                            break;
//                        case MOVED:
//
//                            break;
//                        case REMOVED:
//                            view.removeData(parent);
//                            break;
//
//                    }
//                });


//        view.showLoading(true);
//        buildingListRef = databaseRef.child(DATABASE_BUILDINGS).addChildEventListener(
//                new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                        MyLog.e("onChildAdded");
//                        final BuildingData parent = dataSnapshot.getValue(BuildingData.class);
//                        view.addBuilding(parent);
//                        databaseRef.child(DATABASE_REVIEWS).child(String.valueOf(parent.id))
//                                .addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                        parent.reviewCount = dataSnapshot.getChildrenCount();
//                                        view.swapData(parent);
//                                    }
//
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//
//                                    }
//                                });
//
//                    }
//
//                    @Override
//                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                        MyLog.e("onChildChanged");
//                        view.swapData(dataSnapshot.getValue(BuildingData.class));
//                    }
//
//                    @Override
//                    public void onChildRemoved(DataSnapshot dataSnapshot) {
//                        MyLog.e("onChildRemoved");
//                        view.removeData(dataSnapshot.getValue(BuildingData.class));
//                    }
//
//                    @Override
//                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                        MyLog.e("onChildMoved");
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        MyLog.e("onCancelled");
//                    }
//                }
//        );
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }


    @Override
    public void addNewTask() {

    }

    @Override
    public void activateTask(@NonNull Task activeTask) {

    }

    @Override
    public void clearCompletedTasks() {

    }


}
