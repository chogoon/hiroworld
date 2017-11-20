package kr.co.within.hiroworld.dagger.component;

import dagger.Component;
import kr.co.within.hiroworld.dagger.WatchReviewScope;
import kr.co.within.hiroworld.dagger.module.BuildingListModule;
import kr.co.within.hiroworld.ui.activity.BuildingListActivity;

@WatchReviewScope
@Component(modules = {BuildingListModule.class}, dependencies = AppComponent.class)
public interface BuildingListComponent {

    void inject(BuildingListActivity activity);

}
