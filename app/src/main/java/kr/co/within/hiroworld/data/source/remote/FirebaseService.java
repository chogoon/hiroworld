package kr.co.within.hiroworld.data.source.remote;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kelvinapps.rxfirebase.DataSnapshotMapper;
import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;

import org.joda.time.DateTime;

import java.util.List;

import kr.co.within.hiroworld.R;
import kr.co.within.hiroworld.data.model.RecommendData;
import kr.co.within.hiroworld.data.model.ReviewData;
import kr.co.within.hiroworld.ui.BaseActivity;
import kr.co.within.hiroworld.util.MyLog;
import kr.co.within.hiroworld.util.MyToast;
import kr.co.within.hiroworld.util.SetValueOnSubscribe;
import rx.Completable;
import rx.Observable;

import static kr.co.within.hiroworld.util.Constants.DATABASE_RECOMMENDS;
import static kr.co.within.hiroworld.util.Constants.DATABASE_REVIEWS;
import static kr.co.within.hiroworld.util.Constants.DATABASE_USERS;

/**
 * Created by chogoon on 2017-07-04.
 */

public class FirebaseService {

    private final Context context;
    private final FirebaseAuth firebaseAuth;
    private GoogleApiClient googleApiClient;
    private final DatabaseReference databaseRef;
    private final FirebaseUser firebaseUser;

    public FirebaseService(Context context) {
        this.context = context;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseUser = firebaseAuth.getCurrentUser();
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
        this.databaseRef.keepSynced(true);
    }

    public Intent getUserWithGoogle(BaseActivity activity) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        if(googleApiClient == null){
            googleApiClient = new GoogleApiClient.Builder(activity)
                    .enableAutoManage(activity, connectionResult -> {
                        MyToast.show(context, "Google Play Services error.");
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }

        return Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
    }

    public void sendEditRealTime(String text){

    }

    public Completable sendReviews(@Nullable ReviewData data){
        MyLog.e(data.toString());
        return Completable.create(new SetValueOnSubscribe(databaseRef.child(DATABASE_REVIEWS), data));
    }

    public Observable<List<ReviewData>> loadReviewsByid(int bid){
        MyLog.e(bid + " / " + DateTime.now().getMillis());
        return RxFirebaseDatabase.observeSingleValueEvent(databaseRef.child(DATABASE_REVIEWS).orderByChild(ReviewData.FIELD.bid.name()).equalTo(bid), DataSnapshotMapper.listOf(ReviewData.class));
    }

//    public Observable<User> loadUserById(String id){
//        return RxFirebaseDatabase.observeSingleValueEvent(databaseRef.child(DATABASE_USERS).orderByChild("uid").equalTo(id), DataSnapshotMapper.of(User.class));
//    }

    public Observable<RxFirebaseChildEvent<ReviewData>> getItemReviewObservable(int bid){
        MyLog.e(bid + " / " + DateTime.now().getMillis());
        return RxFirebaseDatabase.observeChildEvent(databaseRef.child(DATABASE_REVIEWS).orderByChild(ReviewData.FIELD.bid.name()).equalTo(bid), ReviewData.class);
    }

    public Observable<RxFirebaseChildEvent<RecommendData>> getItemRecommendObservable(String uid){
//        return RxFirebaseDatabase.observeChildEvent(databaseRef.child(DATABASE_RECOMMENDS).orderByChild(RecommendData.FIELD.uid.name()).equalTo(uid).getRef().startAt(DateTime.now().getMillis()), RecommendData.class);
        return RxFirebaseDatabase.observeChildEvent(databaseRef.child(DATABASE_RECOMMENDS + "/" + uid).orderByChild(RecommendData.FIELD.date.name()).startAt(DateTime.now().getMillis()), RecommendData.class);
    }
}
