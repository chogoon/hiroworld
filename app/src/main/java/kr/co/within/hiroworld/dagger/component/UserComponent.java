package kr.co.within.hiroworld.dagger.component;

import dagger.Subcomponent;
import kr.co.within.hiroworld.dagger.UserScope;
import kr.co.within.hiroworld.dagger.module.UserModule;

@UserScope
@Subcomponent(
        modules = {
                UserModule.class
        }
)
public interface UserComponent {

}
