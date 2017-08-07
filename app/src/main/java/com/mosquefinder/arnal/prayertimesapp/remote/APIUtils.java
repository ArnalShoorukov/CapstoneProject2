package com.mosquefinder.arnal.prayertimesapp.remote;

/**
 * Created by arnal on 8/6/17.
 */

public class APIUtils {
    public static final String BASE_URL = "http://api.alquran.cloud/";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
