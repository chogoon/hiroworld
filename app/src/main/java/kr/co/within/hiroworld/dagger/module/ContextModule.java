package kr.co.within.hiroworld.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import kr.co.within.hiroworld.app.ApplicationContext;
import kr.co.within.hiroworld.dagger.ApplicationScope;

/**
 * Created by chogoon on 2017-05-31.
 */
@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    public Context context(){
        return context;
    }
}
