package kr.co.within.hiroworld.dagger.module;

import android.app.Activity;

import com.twistedequations.mvl.rx.AndroidRxSchedulers;

import dagger.Module;
import dagger.Provides;
import kr.co.within.hiroworld.dagger.InnovationScrollScope;
import kr.co.within.hiroworld.ui.model.InnovationScrollModel;
import kr.co.within.hiroworld.ui.presenter.InnovationScrollPresenter;
import kr.co.within.hiroworld.ui.view.InnovationScrollView;

/**
 * Created by chogoon on 2017-05-31.
 */
@Module
public class InnovationScrollModule {

    private final Activity activity;

    public InnovationScrollModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @InnovationScrollScope
    public InnovationScrollView view(){
        return new InnovationScrollView(activity);
    }

    @Provides
    @InnovationScrollScope
    public InnovationScrollModel model(){
        return new InnovationScrollModel(activity);
    }

    @Provides
    @InnovationScrollScope
    public InnovationScrollPresenter presenter(InnovationScrollView view, InnovationScrollModel model, AndroidRxSchedulers androidSchedulers){
        return new InnovationScrollPresenter(view, model, androidSchedulers);
    }


}
