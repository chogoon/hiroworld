package kr.co.within.hiroworld.dagger.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import dagger.Module;
import dagger.Provides;
import kr.co.within.hiroworld.BuildConfig;
import kr.co.within.hiroworld.dagger.ApplicationScope;
import kr.co.within.hiroworld.data.network.ApiService;
import kr.co.within.hiroworld.data.network.DateTimeConverter;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chogoon on 2017-05-31.
 */

@Module(includes = NetworkModule.class)
public class AppServiceModule {

    @Provides
    @ApplicationScope
    public ApiService githubService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

    @Provides
    public Gson gson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeConverter());
        return gsonBuilder.create();
    }

    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient, Gson gson){
         return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                 .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                 .client(okHttpClient)
                .baseUrl(BuildConfig.BASEURL)
                .build();
    }


}
