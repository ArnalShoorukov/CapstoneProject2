package com.mosquefinder.arnal.prayertimesapp.Quran;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by arnal on 8/6/17.
 */

public class Surah implements Parcelable {
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("englishName")
    @Expose
    private String englishName;
    @SerializedName("englishNameTranslation")
    @Expose
    private String englishNameTranslation;
    @SerializedName("revelationType")
    @Expose
    private String revelationType;
    @SerializedName("numberOfAyahs")
    @Expose
    private Integer numberOfAyahs;
    public final static Parcelable.Creator<Surah> CREATOR = new Creator<Surah>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Surah createFromParcel(Parcel in) {
            Surah instance = new Surah();
            instance.number = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.englishName = ((String) in.readValue((String.class.getClassLoader())));
            instance.englishNameTranslation = ((String) in.readValue((String.class.getClassLoader())));
            instance.revelationType = ((String) in.readValue((String.class.getClassLoader())));
            instance.numberOfAyahs = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public Surah[] newArray(int size) {
            return (new Surah[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public Surah() {
    }

    /**
     * @param englishName
     * @param numberOfAyahs
     * @param name
     * @param number
     * @param revelationType
     * @param englishNameTranslation
     */
    public Surah(Integer number, String name, String englishName, String englishNameTranslation, String revelationType, Integer numberOfAyahs) {
        super();
        this.number = number;
        this.name = name;
        this.englishName = englishName;
        this.englishNameTranslation = englishNameTranslation;
        this.revelationType = revelationType;
        this.numberOfAyahs = numberOfAyahs;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getEnglishNameTranslation() {
        return englishNameTranslation;
    }

    public void setEnglishNameTranslation(String englishNameTranslation) {
        this.englishNameTranslation = englishNameTranslation;
    }

    public String getRevelationType() {
        return revelationType;
    }

    public void setRevelationType(String revelationType) {
        this.revelationType = revelationType;
    }

    public Integer getNumberOfAyahs() {
        return numberOfAyahs;
    }

    public void setNumberOfAyahs(Integer numberOfAyahs) {
        this.numberOfAyahs = numberOfAyahs;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(number);
        dest.writeValue(name);
        dest.writeValue(englishName);
        dest.writeValue(englishNameTranslation);
        dest.writeValue(revelationType);
        dest.writeValue(numberOfAyahs);
    }

    public int describeContents() {
        return 0;
    }

}