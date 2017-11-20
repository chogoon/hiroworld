package kr.co.within.hiroworld.dagger.component;

import dagger.Component;
import kr.co.within.hiroworld.dagger.InnovationScrollScope;
import kr.co.within.hiroworld.dagger.module.InnovationScrollModule;
import kr.co.within.hiroworld.ui.activity.InnovationScrollActivity;
import kr.co.within.hiroworld.ui.activity.ReviewWriteActivity;

/**
 * Created by chogoon on 2017-05-31.
 */
@InnovationScrollScope
@Component(modules = {InnovationScrollModule.class}, dependencies = AppComponent.class)
public interface InnovationScrollComponent {

    void inject(InnovationScrollActivity activity);

}
