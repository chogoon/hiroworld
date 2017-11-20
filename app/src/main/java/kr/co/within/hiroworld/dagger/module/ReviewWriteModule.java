package kr.co.within.hiroworld.dagger.module;

import android.app.Activity;

import com.squareup.picasso.Picasso;
import com.twistedequations.mvl.rx.AndroidRxSchedulers;

import dagger.Module;
import dagger.Provides;
import kr.co.within.hiroworld.dagger.ReviewWriteScope;
import kr.co.within.hiroworld.data.source.remote.FirebaseService;
import kr.co.within.hiroworld.ui.model.ReviewWriteModel;
import kr.co.within.hiroworld.ui.model.SplashModel;
import kr.co.within.hiroworld.ui.presenter.ReviewWritePresenter;
import kr.co.within.hiroworld.ui.view.ReviewWriteView;
import kr.co.within.hiroworld.ui.view.SplashView;

/**
 * Created by chogoon on 2017-05-31.
 */
@Module
public class ReviewWriteModule {

    private final Activity activity;

    public ReviewWriteModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ReviewWriteScope
    public ReviewWriteView view(){
        return new ReviewWriteView(activity);
    }

    @Provides
    @ReviewWriteScope
    public ReviewWriteModel model(FirebaseService service){
        return new ReviewWriteModel(activity, service);
    }

    @Provides
    @ReviewWriteScope
    public ReviewWritePresenter presenter(ReviewWriteView view, ReviewWriteModel model, AndroidRxSchedulers androidSchedulers){
        return new ReviewWritePresenter(view, model, androidSchedulers);
    }


}
