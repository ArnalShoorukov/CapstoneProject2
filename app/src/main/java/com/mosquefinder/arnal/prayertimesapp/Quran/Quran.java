package com.mosquefinder.arnal.prayertimesapp.Quran;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by arnal on 8/6/17.
 */

public class Quran implements Parcelable {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;
    public final static Parcelable.Creator<Quran> CREATOR = new Creator<Quran>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Quran createFromParcel(Parcel in) {
            Quran instance = new Quran();
            instance.code = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            instance.data = ((Data) in.readValue((Data.class.getClassLoader())));
            return instance;
        }

        public Quran[] newArray(int size) {
            return (new Quran[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public Quran() {
    }

    /**
     * @param status
     * @param data
     * @param code
     */
    public Quran(Integer code, String status, Data data) {
        super();
        this.code = code;
        this.status = status;
        this.data = data;
    }

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(status);
        dest.writeValue(data);
    }

    public int describeContents() {
        return 0;
    }

}