package kr.co.within.hiroworld.app;

import android.app.Application;
import android.content.Context;

import com.firebase.ui.auth.ui.User;
import com.google.firebase.database.FirebaseDatabase;

import net.danlew.android.joda.JodaTimeAndroid;

import kr.co.within.hiroworld.BuildConfig;
import kr.co.within.hiroworld.dagger.component.AppComponent;
import kr.co.within.hiroworld.dagger.component.DaggerAppComponent;
import kr.co.within.hiroworld.dagger.component.UserComponent;
import kr.co.within.hiroworld.dagger.module.ContextModule;
import kr.co.within.hiroworld.dagger.module.UserModule;
import kr.co.within.hiroworld.util.Constants;
import timber.log.Timber;

/**
 * Created by chogoon on 2017-07-03.
 */

public class MyApplication extends Application {

    private AppComponent component;
    private UserComponent userComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree(){
                @Override
                protected void log(int priority, String tag, String message, Throwable t) {
                    super.log(priority, Constants.LOGTAG, message, t);
                }
            });
//            AndroidDevMetrics.initWith(this);
        }
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        JodaTimeAndroid.init(this);
        component = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

    }

    public AppComponent component(){
        return component;
    }

    public UserComponent createUserComponent(User user) {
        userComponent = component.plus(new UserModule(user));
        return userComponent;
    }

    public UserComponent getUserComponent() {
        return userComponent;
    }

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }


}
