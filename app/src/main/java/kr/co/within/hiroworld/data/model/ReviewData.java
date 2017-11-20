package kr.co.within.hiroworld.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.firebase.ui.auth.ui.User;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class ReviewData implements Comparable<ReviewData>, Parcelable{

    public enum FIELD{
        bid, uid, content, write_date
    }

    public int bid;
    public String uid;
    public String content;
    public long write_date;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bid);
        dest.writeString(uid);
        dest.writeString(content);
        dest.writeLong(write_date);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ReviewData createFromParcel(Parcel in) {
            return new ReviewData(in);
        }
        public ReviewData[] newArray(int size) {
            return new ReviewData[size];
        }
    };

    private ReviewData(Parcel in) {
        bid = in.readInt();
        uid = in.readString();
        content = in.readString();
        write_date = in.readLong();
    }

    @Override
    public int compareTo(@NonNull ReviewData o) {
        if(write_date < o.write_date){
            return 1;
        }else if (write_date > o.write_date) return -1;
        return 0;
    }

    public ReviewData() { }

    public ReviewData(String user_id, String content, long write_date) {
        this.uid = user_id;
        this.content = content;
        this.write_date = write_date;
    }

    @Override
    public String toString() {
        return "ReviewData{" +
                "bid=" + bid +
                ", uid='" + uid + '\'' +
                ", content='" + content + '\'' +
                ", write_date=" + write_date +
                '}';
    }
}