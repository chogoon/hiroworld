package kr.co.within.hiroworld.ui.model;

import android.app.Activity;
import android.view.View;

import com.firebase.ui.auth.ui.User;
import com.google.firebase.auth.FirebaseUser;

import kr.co.within.hiroworld.app.MyApplication;
import kr.co.within.hiroworld.data.source.remote.FirebaseService;
import kr.co.within.hiroworld.ui.BaseActivity;
import kr.co.within.hiroworld.ui.activity.BuildingListActivity;
import kr.co.within.hiroworld.ui.activity.CollapsingActivity;
import kr.co.within.hiroworld.ui.activity.InnovationScrollActivity;
import kr.co.within.hiroworld.ui.activity.SvgVectorDrawableActivity;
import kr.co.within.hiroworld.util.MyLog;
import rx.Observable;

/**
 * Created by chogoon on 2017-07-03.
 */

public class SplashModel {

    public enum SplashMenus {
        REVIEW("Watch Review"),
        SVG("SVG VectorDrawables"),
        INNOVATION_SCROLL("혁신 프로젝트 PREVIEW BY SCROLL"),
        LOGOUT("Log Out"),
        COLLAPSING("Collapsing"),
        ETC("Etc");

        private String name;

        SplashMenus(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static final String[] names = new String[values().length];

        static {
            SplashMenus[] values = values();
            for (int i = 0; i < values.length; i++) {
                names[i] = values[i].name();
            }
        }
    }

    public static final int REQUEST_SIGN_GOOGLE = 1101;
    public static final String LIST_STATE_KEY = "SPLASH_STATE_KEY";
    private final Activity activity;
    private final FirebaseService fbService;


    public SplashModel(Activity activity, FirebaseService fbService) {
        this.activity = activity;
        this.fbService = fbService;

    }

    public void loginWithGoogle() {
        activity.startActivityForResult(fbService.getUserWithGoogle((BaseActivity) activity), REQUEST_SIGN_GOOGLE);
    }

    public void createUserComponent(FirebaseUser user) {
        MyApplication.get(activity).createUserComponent(new User.Builder(user.getEmail()).setName(user.getDisplayName()).setPhotoUri(user.getPhotoUrl()).setProvider(user.getProviderId()).build());
    }

    public Activity getActivity() {
        return activity;
    }

    public Observable<SplashMenus> loadMenuList() {
//        return Observable.from(new ArrayList<>(Arrays.asList(SplashMenus.names)));
        return Observable.from(SplashMenus.values());
    }

    public void startActivity(View view) {
        final SplashMenus menu = (SplashMenus) view.getTag();
        MyLog.e(menu);
        switch (menu) {
            case REVIEW:
                BuildingListActivity.start(activity);
                break;
            case SVG:
                SvgVectorDrawableActivity.start(activity);
                break;
            case INNOVATION_SCROLL:
                InnovationScrollActivity.start(activity);
                break;
            case COLLAPSING:
                CollapsingActivity.start(activity);
                break;

        }

    }

}
