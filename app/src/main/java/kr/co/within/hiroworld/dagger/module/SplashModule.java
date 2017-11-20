package kr.co.within.hiroworld.dagger.module;

import android.app.Activity;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import kr.co.within.hiroworld.dagger.SplashScope;
import kr.co.within.hiroworld.data.source.remote.FirebaseService;
import kr.co.within.hiroworld.ui.model.SplashModel;
import kr.co.within.hiroworld.ui.presenter.SplashPresenter;
import kr.co.within.hiroworld.ui.view.SplashView;

/**
 * Created by chogoon on 2017-05-31.
 */
@Module
public class SplashModule {

    private final Activity activity;

    public SplashModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @SplashScope
    public SplashView view(Picasso picasso){
        return new SplashView(activity, picasso);
    }

    @Provides
    @SplashScope
    public SplashModel model(FirebaseService service){
        return new SplashModel(activity, service);
    }

    @Provides
    @SplashScope
    public SplashPresenter presenter(SplashView view, SplashModel model){
        return new SplashPresenter(view, model);
    }


}
