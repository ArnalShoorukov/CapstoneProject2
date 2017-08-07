package com.mosquefinder.arnal.prayertimesapp.Quran;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by arnal on 8/6/17.
 */

public class Ayah implements Parcelable {

    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("surah")
    @Expose
    private Surah surah;
    @SerializedName("numberInSurah")
    @Expose
    private Integer numberInSurah;
    @SerializedName("juz")
    @Expose
    private Integer juz;
    @SerializedName("manzil")
    @Expose
    private Integer manzil;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("ruku")
    @Expose
    private Integer ruku;
    @SerializedName("hizbQuarter")
    @Expose
    private Integer hizbQuarter;
    @SerializedName("sajda")
    @Expose
    private Boolean sajda;
    public final static Parcelable.Creator<Ayah> CREATOR = new Creator<Ayah>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Ayah createFromParcel(Parcel in) {
            Ayah instance = new Ayah();
            instance.number = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.text = ((String) in.readValue((String.class.getClassLoader())));
            instance.surah = ((Surah) in.readValue((Surah.class.getClassLoader())));
            instance.numberInSurah = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.juz = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.manzil = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.ruku = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.hizbQuarter = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.sajda = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            return instance;
        }

        public Ayah[] newArray(int size) {
            return (new Ayah[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public Ayah() {
    }

    /**
     * @param text
     * @param page
     * @param sajda
     * @param juz
     * @param ruku
     * @param manzil
     * @param number
     * @param numberInSurah
     * @param hizbQuarter
     * @param surah
     */
    public Ayah(Integer number, String text, Surah surah, Integer numberInSurah, Integer juz, Integer manzil, Integer page, Integer ruku, Integer hizbQuarter, Boolean sajda) {
        super();
        this.number = number;
        this.text = text;
        this.surah = surah;
        this.numberInSurah = numberInSurah;
        this.juz = juz;
        this.manzil = manzil;
        this.page = page;
        this.ruku = ruku;
        this.hizbQuarter = hizbQuarter;
        this.sajda = sajda;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Surah getSurah() {
        return surah;
    }

    public void setSurah(Surah surah) {
        this.surah = surah;
    }

    public Integer getNumberInSurah() {
        return numberInSurah;
    }

    public void setNumberInSurah(Integer numberInSurah) {
        this.numberInSurah = numberInSurah;
    }

    public Integer getJuz() {
        return juz;
    }

    public void setJuz(Integer juz) {
        this.juz = juz;
    }

    public Integer getManzil() {
        return manzil;
    }

    public void setManzil(Integer manzil) {
        this.manzil = manzil;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRuku() {
        return ruku;
    }

    public void setRuku(Integer ruku) {
        this.ruku = ruku;
    }

    public Integer getHizbQuarter() {
        return hizbQuarter;
    }

    public void setHizbQuarter(Integer hizbQuarter) {
        this.hizbQuarter = hizbQuarter;
    }

    public Boolean getSajda() {
        return sajda;
    }

    public void setSajda(Boolean sajda) {
        this.sajda = sajda;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(number);
        dest.writeValue(text);
        dest.writeValue(surah);
        dest.writeValue(numberInSurah);
        dest.writeValue(juz);
        dest.writeValue(manzil);
        dest.writeValue(page);
        dest.writeValue(ruku);
        dest.writeValue(hizbQuarter);
        dest.writeValue(sajda);
    }

    public int describeContents() {
        return 0;
    }
}
