package kr.co.within.hiroworld.dagger.module;

import android.content.Context;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import kr.co.within.hiroworld.app.ApplicationContext;
import kr.co.within.hiroworld.dagger.ApplicationScope;
import kr.co.within.hiroworld.dagger.module.ContextModule;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by chogoon on 2017-05-31.
 */

@Module(includes = ContextModule.class)
public class NetworkModule {

    @Provides
    @ApplicationScope
    public HttpLoggingInterceptor loggingInterceptor(){
        HttpLoggingInterceptor interceptor =  new HttpLoggingInterceptor(message -> Timber.i(message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    @Provides
    @ApplicationScope
    public Cache cache(File cacheFile){
        cacheFile.mkdirs();
        return new Cache(cacheFile, 10 * 1000 * 1000); //10MB
    }

    @Provides
    @ApplicationScope
    public File cacheFile(@ApplicationContext Context context){
        return new File(context.getCacheDir(), "okhttp_cache");
    }

    @Provides
    @ApplicationScope
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache){
        return  new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
    }
}
