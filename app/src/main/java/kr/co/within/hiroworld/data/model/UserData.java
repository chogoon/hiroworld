package kr.co.within.hiroworld.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserData implements Parcelable {

    public String displayName;
    public String email;
    public String photoURL;
    public String uid;
//    public String provider;



    public UserData() { }

    private UserData(Parcel in) {
        email = in.readString();
        displayName = in.readString();
        photoURL = in.readString();
        uid = in.readString();
//        provider = in.readString();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
        dest.writeString(email);
//        dest.writeString(provider);
        dest.writeString(photoURL);
        dest.writeString(uid);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserData createFromParcel(Parcel in) { return new UserData(in); }
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    @Override
    public String toString() {
        return "UserData{" +
                "email='" + email + '\'' +
                ", displayName='" + displayName + '\'' +
                ", uid='" + uid + '\'' +
                ", photoUri='" + photoURL + '\'' +
                '}';
    }
}
