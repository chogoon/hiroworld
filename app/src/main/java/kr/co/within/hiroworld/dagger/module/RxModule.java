package kr.co.within.hiroworld.dagger.module;


import com.twistedequations.mvl.rx.AndroidRxSchedulers;
import com.twistedequations.mvl.rx.DefaultAndroidRxSchedulers;

import dagger.Module;
import dagger.Provides;
import kr.co.within.hiroworld.dagger.ApplicationScope;

@Module
public class RxModule {

    @Provides
    @ApplicationScope
    public AndroidRxSchedulers rxSchedulers() {
        return new DefaultAndroidRxSchedulers();
    }
}
