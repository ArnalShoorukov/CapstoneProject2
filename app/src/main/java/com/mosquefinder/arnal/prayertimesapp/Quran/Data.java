package com.mosquefinder.arnal.prayertimesapp.Quran;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by arnal on 8/6/17.
 */

public class Data implements Parcelable {

    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("ayahs")
    @Expose
    private List<Ayah> ayahs = null;
    @SerializedName("surahs")
    @Expose
    private Surahs surahs;
    @SerializedName("edition")
    @Expose
    private Edition edition;
    public final static Parcelable.Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            Data instance = new Data();
            instance.number = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.surahs = ((Surahs) in.readValue((Surahs.class.getClassLoader())));
            instance.edition = ((Edition) in.readValue((Edition.class.getClassLoader())));
            return instance;
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public Data() {
    }

    /**
     * @param edition
     * @param surahs
     * @param number
     * @param ayahs
     */
    public Data(Integer number, List<Ayah> ayahs, Surahs surahs, Edition edition) {
        super();
        this.number = number;
        this.ayahs = ayahs;
        this.surahs = surahs;
        this.edition = edition;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<Ayah> getAyahs() {
        return ayahs;
    }

    public void setAyahs(List<Ayah> ayahs) {
        this.ayahs = ayahs;
    }

    public Surahs getSurahs() {
        return surahs;
    }

    public void setSurahs(Surahs surahs) {
        this.surahs = surahs;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(number);
        dest.writeList(ayahs);
        dest.writeValue(surahs);
        dest.writeValue(edition);
    }

    public int describeContents() {
        return 0;
    }

}