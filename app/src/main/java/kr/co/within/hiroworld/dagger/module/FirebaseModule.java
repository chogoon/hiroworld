package kr.co.within.hiroworld.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import kr.co.within.hiroworld.app.ApplicationContext;
import kr.co.within.hiroworld.dagger.ApplicationScope;
import kr.co.within.hiroworld.data.source.remote.FirebaseService;

/**
 * Created by chogoon on 2017-07-04.
 */
@Module
public class FirebaseModule {

    @Provides
    @ApplicationScope
    public FirebaseService provideFirebaseService(@ApplicationContext Context context) {
        return new FirebaseService(context);
    }


}
