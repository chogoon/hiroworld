package kr.co.within.hiroworld.dagger.module;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import kr.co.within.hiroworld.dagger.ApplicationScope;

@Module
public class ActivityModule {

    private final Activity context;

    public ActivityModule(Activity context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    public Context context(){
        return context;
    }
}
