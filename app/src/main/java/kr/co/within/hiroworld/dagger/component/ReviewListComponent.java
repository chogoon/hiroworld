package kr.co.within.hiroworld.dagger.component;

import dagger.Component;
import kr.co.within.hiroworld.dagger.ReviewListScope;
import kr.co.within.hiroworld.dagger.module.ReviewListModule;
import kr.co.within.hiroworld.ui.activity.ReviewListActivity;

/**
 * Created by chogoon on 2017-05-31.
 */
@ReviewListScope
@Component(modules = {ReviewListModule.class}, dependencies = AppComponent.class)
public interface ReviewListComponent {

    void inject(ReviewListActivity activity);

}
