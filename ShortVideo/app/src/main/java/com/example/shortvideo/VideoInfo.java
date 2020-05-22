package com.example.shortvideo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class VideoInfo implements Parcelable {
    @SerializedName("_id")
    public String id;
    @SerializedName("feedurl")
    public String feedurl;
    @SerializedName("nickname")
    public String nickname;
    @SerializedName("description")
    public String description;
    @SerializedName("likecount")
    public String likecount;
    @SerializedName("avatar")
    public String avatar;

    protected VideoInfo(Parcel in) {
        id = in.readString();
        feedurl = in.readString();
        nickname = in.readString();
        description = in.readString();
        likecount = in.readString();
        avatar = in.readString();
    }

    public static final Creator<VideoInfo> CREATOR = new Creator<VideoInfo>() {
        @Override
        public VideoInfo createFromParcel(Parcel in) {
            return new VideoInfo(in);
        }

        @Override
        public VideoInfo[] newArray(int size) {
            return new VideoInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(feedurl);
        dest.writeString(nickname);
        dest.writeString(description);
        dest.writeString(likecount);
        dest.writeString(avatar);
    }
}

