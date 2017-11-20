package kr.co.within.hiroworld.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class RecommendData implements Comparable<RecommendData>, Parcelable{

    public enum FIELD{
        bid, uid, content, date
    }

    public int building_id;
    public String uid;
    public String content;
    public long date;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(building_id);
        dest.writeString(uid);
        dest.writeString(content);
        dest.writeLong(date);
    }

    public static final Creator CREATOR = new Creator() {
        public RecommendData createFromParcel(Parcel in) {
            return new RecommendData(in);
        }
        public RecommendData[] newArray(int size) {
            return new RecommendData[size];
        }
    };

    private RecommendData(Parcel in) {
        building_id = in.readInt();
        uid = in.readString();
        content = in.readString();
        date = in.readLong();
    }

    @Override
    public int compareTo(@NonNull RecommendData o) {
        if(date < o.date){
            return 1;
        }else if (date > o.date) return -1;
        return 0;
    }

    public RecommendData() { }

    public RecommendData(String user_id, String content, long write_date) {
        this.uid = user_id;
        this.content = content;
        this.date = write_date;
    }

    @Override
    public String toString() {
        return "RecommendData{" +
                "building_id=" + building_id +
                ", uid='" + uid + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}