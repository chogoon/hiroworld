package kr.co.within.hiroworld.ui.model;

import android.app.Activity;
import android.view.View;

import kr.co.within.hiroworld.data.model.BuildingData;
import kr.co.within.hiroworld.data.model.BuildingListData;
import kr.co.within.hiroworld.data.network.ApiService;
import kr.co.within.hiroworld.data.source.remote.FirebaseService;
import kr.co.within.hiroworld.ui.BaseModel;
import kr.co.within.hiroworld.ui.activity.ReviewListActivity;
import rx.Observable;

/**
 * Created by chogoon on 2017-07-03.
 */

public class BuildingListModel implements BaseModel{

    public static final int REQUEST_SIGN_GOOGLE = 1101;
    private final Activity activity;
    private final FirebaseService fbService;
    private final ApiService apiService;


    public BuildingListModel(Activity activity, FirebaseService fbService, ApiService apiService) {
        this.activity = activity;
        this.fbService = fbService;
        this.apiService = apiService;

    }

    public Activity getActivity() {
        return activity;
    }

    public Observable<BuildingListData> getSearchBuildingList(String searchWord){
        return apiService.getSearchBuildingList(searchWord);
    }

//    public void getBuildingList(){
//        apiService.getSampleBuildingList().enqueue(new Callback<BuildingListData>() {
//            @Override
//            public void onResponse(Call<BuildingListData> call, Response<BuildingListData> response) {
//                fbService.
//            }
//
//            @Override
//            public void onFailure(Call<BuildingListData> call, Throwable t) {
//                MyToast.show(activity, "Error getting repos " + t.getMessage(), Toast.LENGTH_SHORT);
//            }
//        });
//    }

    public void startActivity(View view) {
        final BuildingData data = (BuildingData) view.getTag();
        ReviewListActivity.start(activity, data);
    }

}
