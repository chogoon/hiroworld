package kr.co.within.hiroworld.dagger.component;

import dagger.Component;
import kr.co.within.hiroworld.dagger.ApplicationScope;
import kr.co.within.hiroworld.dagger.SplashScope;
import kr.co.within.hiroworld.dagger.module.SplashModule;
import kr.co.within.hiroworld.ui.activity.SplashActivity;

/**
 * Created by chogoon on 2017-05-31.
 */
@SplashScope
@Component(modules = {SplashModule.class}, dependencies = AppComponent.class)
public interface SplashComponent {

    void inject(SplashActivity activity);

}
