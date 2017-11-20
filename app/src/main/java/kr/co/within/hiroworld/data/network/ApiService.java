package kr.co.within.hiroworld.data.network;


import kr.co.within.hiroworld.data.model.BuildingListData;
import retrofit2.Call;
import retrofit2.http.GET;
import kr.co.within.hiroworld.data.model.BaseData;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

//@GET("/v1/ads?arino=3006&adcno=3&udlat=37.47700320704257&sel_date2=2017-06-01&sel_date=2017-05-31&udlng=126.89790889620781&promotion=N&reserve=0%2C0&sort=HIT")
//Call<BaseData> getList();


@GET("/v1/search")
Observable<BuildingListData> getSearchBuildingList(@Query("keyword") String word);

}
