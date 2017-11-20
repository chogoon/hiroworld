package kr.co.within.hiroworld.dagger.module;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;

import com.squareup.picasso.Picasso;
import com.twistedequations.mvl.rx.AndroidRxSchedulers;

import dagger.Module;
import dagger.Provides;
import kr.co.within.hiroworld.dagger.ReviewListScope;
import kr.co.within.hiroworld.data.source.remote.FirebaseService;
import kr.co.within.hiroworld.ui.model.ReviewListModel;
import kr.co.within.hiroworld.ui.presenter.ReviewListPresenter;
import kr.co.within.hiroworld.ui.view.ReviewListView;

@Module
public class ReviewListModule {

    private final Activity activity;

    public ReviewListModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ReviewListScope
    public ReviewListView view(Picasso picasso, LinearLayoutManager linearLayoutManager){
        return new ReviewListView(activity, picasso, linearLayoutManager);
    }

    @Provides
    @ReviewListScope
    public ReviewListModel model(FirebaseService service){
        return new ReviewListModel(activity, service);
    }

    @Provides
    @ReviewListScope
    public ReviewListPresenter presenter(ReviewListView view, ReviewListModel model, AndroidRxSchedulers androidSchedulers){
        return new ReviewListPresenter(view, model, androidSchedulers);
    }

    @Provides
    @ReviewListScope
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(activity);
    }


}
