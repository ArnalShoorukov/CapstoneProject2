package com.mosquefinder.arnal.prayertimesapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by arnal on 7/23/17.
 */

public class Dua implements Parcelable{
    private String mId;
    private String mReference;
    private String mTitle;
    private String mEnglish;
    private String mBenefit;
    private int mAudioResourceID;
    private int mImageResourceID;


    public Dua(String id, String title, String english, String reference,String benefit, int audioResourceId, int imageResourceID)
    {
        mId= id;
        mTitle = title;
        mEnglish = english;
        mReference = reference;
        mBenefit = benefit;
        mAudioResourceID = audioResourceId;
        mImageResourceID = imageResourceID;

    }

    protected Dua(Parcel in) {
        mId = in.readString();
        mReference = in.readString();
        mTitle = in.readString();
        mEnglish = in.readString();
        mBenefit = in.readString();
        mAudioResourceID = in.readInt();
        mImageResourceID = in.readInt();
    }

    public static final Creator<Dua> CREATOR = new Creator<Dua>() {
        @Override
        public Dua createFromParcel(Parcel in) {
            return new Dua(in);
        }

        @Override
        public Dua[] newArray(int size) {
            return new Dua[size];
        }
    };

    public String getEnglish()
    {
        return mEnglish;
    }
    public String getId()
    {
        return mId;
    }

    public String getReference()
    {
        return mReference;
    }

    public String getBenefit()
    {
        return mBenefit;
    }

    public String getmTitle(){return mTitle; }

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
        dest.writeString(mId);
        dest.writeString(mReference);
        dest.writeString(mTitle);
        dest.writeString(mEnglish);
        dest.writeString(mBenefit);
        dest.writeInt(mAudioResourceID);
        dest.writeInt(mImageResourceID);
    }
}
