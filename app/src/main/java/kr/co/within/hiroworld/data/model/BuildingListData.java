package kr.co.within.hiroworld.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chogoon on 2017-05-31.
 */

public class BuildingListData extends BaseData{

    public BuildingListData data;

    @SerializedName("total_count")
    public int totalCount;

    @SerializedName("total_page_cnt")
    public int totalPageCount;

    @SerializedName("list")
    public List<BuildingData> result;


}
