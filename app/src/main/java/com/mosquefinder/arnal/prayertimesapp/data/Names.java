package com.mosquefinder.arnal.prayertimesapp.data;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by arnal on 8/1/17.
 */

public class Names implements Parcelable {

    private int mAudioResourceID;
    private int mImageResourceID;

    public Names(int audioResourceId, int imageResourceID)
    {

        mAudioResourceID = audioResourceId;
        mImageResourceID = imageResourceID;
    }

    protected Names(Parcel in) {
        mAudioResourceID = in.readInt();
        mImageResourceID = in.readInt();
    }

    public static final Creator<Names> CREATOR = new Creator<Names>() {
        @Override
        public Names createFromParcel(Parcel in) {
            return new Names(in);
        }

        @Override
        public Names[] newArray(int size) {
            return new Names[size];
        }
    };

    public int getImageResourceID()
    {
        return mImageResourceID;
    }

    public int getAudioResourceId(){return mAudioResourceID;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mAudioResourceID);
        dest.writeInt(mImageResourceID);
    }
}
