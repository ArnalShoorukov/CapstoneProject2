package com.mosquefinder.arnal.prayertimesapp.Quran;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnal on 8/6/17.
 */

public class QueryResult implements Parcelable {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;


    protected QueryResult(Parcel in) {
        status = in.readString();
        data = in.readParcelable(Data.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeParcelable(data, flags);
    }

    public static final Creator<QueryResult> CREATOR = new Creator<QueryResult>() {
        @Override
        public QueryResult createFromParcel(Parcel in) {
            return new QueryResult(in);
        }

        @Override
        public QueryResult[] newArray(int size) {
            return new QueryResult[size];
        }
    };

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getDataResult() {
        return data;
    }

    public void setDataResult(Data data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
