package kr.co.within.hiroworld.data.model;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by chogoon on 2017-05-31.
 */

public class BaseData {

    @SerializedName("sel_date")
    public DateTime start_date;

    @SerializedName("sel_date2")
    public DateTime end_date;

    @SerializedName("count")
    public int count;

    public int code;

}
