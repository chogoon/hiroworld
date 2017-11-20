package kr.co.within.hiroworld.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chogoon on 2017-05-31.
 */

public class ReviewListData implements Parcelable {

    @SerializedName("list")
    public List<ReviewData> data;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.data);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ReviewListData createFromParcel(Parcel in) {
            return new ReviewListData(in);
        }
        public ReviewListData[] newArray(int size) {
            return new ReviewListData[size];
        }
    };

    protected ReviewListData(Parcel in) {
        this.data = in.createTypedArrayList(ReviewData.CREATOR);
    }

    public ReviewListData() { }

    public ReviewListData(List<ReviewData> data) {
        this.data = data;
    }
}
