package kr.co.within.hiroworld.dagger.module;

import com.firebase.ui.auth.ui.User;

import dagger.Module;
import dagger.Provides;
import kr.co.within.hiroworld.dagger.UserScope;
import kr.co.within.hiroworld.data.source.remote.BuildingService;

@Module
public class UserModule {
    User user;

    public UserModule(User user) {
        this.user = user;
    }

    @Provides
    @UserScope
    User user() {
        return user;
    }

    @Provides
    @UserScope
    BuildingService buildingService() {
        return new BuildingService(user);
    }
}
