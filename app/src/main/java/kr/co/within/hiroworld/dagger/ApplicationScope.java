package kr.co.within.hiroworld.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by chogoon on 2017-07-03.
 */

@Scope // == @Singleton
@Retention(RetentionPolicy.CLASS)
public @interface ApplicationScope {

}
