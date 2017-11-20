package kr.co.within.hiroworld.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chogoon on 2017-05-31.
 */

public class BuildingData implements Comparable<BuildingData>, Parcelable {

    @SerializedName("ano")
    public int id;

    @SerializedName("imgpath")
    public String imagePath;

    @SerializedName("aname")
    public String name;

    public List<ReviewData> reviews = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildingData itemData = (BuildingData) o;
        return id == itemData.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(@NonNull BuildingData o) {
        if(id < o.id){
            return 1;
        }else if (id > o.id) return -1;
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(imagePath);
        dest.writeString(name);
        dest.writeList(reviews);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public BuildingData createFromParcel(Parcel in) {
            return new BuildingData(in);
        }
        public BuildingData[] newArray(int size) {
            return new BuildingData[size];
        }
    };

    private BuildingData(Parcel in) {
        id = in.readInt();
        name = in.readString();
        imagePath = in.readString();
        reviews = new ArrayList<>();
        in.readTypedList(reviews, ReviewData.CREATOR);
    }

    public BuildingData() { }
}
