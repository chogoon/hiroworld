package kr.co.within.hiroworld.dagger.component;

import dagger.Component;
import kr.co.within.hiroworld.dagger.ReviewWriteScope;
import kr.co.within.hiroworld.dagger.SplashScope;
import kr.co.within.hiroworld.dagger.module.ReviewWriteModule;
import kr.co.within.hiroworld.ui.activity.ReviewWriteActivity;

/**
 * Created by chogoon on 2017-05-31.
 */
@ReviewWriteScope
@Component(modules = {ReviewWriteModule.class}, dependencies = AppComponent.class)
public interface ReviewWriteComponent {

    void inject(ReviewWriteActivity activity);

}
