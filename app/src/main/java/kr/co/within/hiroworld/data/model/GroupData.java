package kr.co.within.hiroworld.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chogoon on 2017-05-31.
 */

public class GroupData {

    @SerializedName("ad_group_no")
    public int grouNo;

    @SerializedName("ad_group_name")
    public String groupName;

    public List<BuildingData> items;

    

}
