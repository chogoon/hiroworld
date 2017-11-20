package kr.co.within.hiroworld.dagger.component;

import com.squareup.picasso.Picasso;
import com.twistedequations.mvl.rx.AndroidRxSchedulers;

import dagger.Component;
import kr.co.within.hiroworld.dagger.ApplicationScope;
import kr.co.within.hiroworld.dagger.module.ActivityModule;
import kr.co.within.hiroworld.dagger.module.AppServiceModule;
import kr.co.within.hiroworld.dagger.module.FirebaseModule;
import kr.co.within.hiroworld.dagger.module.PicassoModule;
import kr.co.within.hiroworld.dagger.module.RxModule;
import kr.co.within.hiroworld.dagger.module.UserModule;
import kr.co.within.hiroworld.data.network.ApiService;
import kr.co.within.hiroworld.data.source.remote.FirebaseService;

/**
 * Created by chogoon on 2017-07-03.
 */

@ApplicationScope
@Component(
        modules = {
                ActivityModule.class,
                AppServiceModule.class,
                PicassoModule.class,
                RxModule.class,
                FirebaseModule.class
        }
)
public interface AppComponent {

    ApiService getApiService();

    FirebaseService getFirebaseService();

    Picasso getPicasso();

    UserComponent plus(UserModule userModule);

    AndroidRxSchedulers rxSchedulers();

}
