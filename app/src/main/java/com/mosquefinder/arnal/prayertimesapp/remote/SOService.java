package com.mosquefinder.arnal.prayertimesapp.remote;

import com.mosquefinder.arnal.prayertimesapp.Quran.Data;
import com.mosquefinder.arnal.prayertimesapp.Quran.QueryResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by arnal on 8/6/17.
 */

public interface SOService {

    @GET("page/{page}/quran-uthmani")
    Call<List<QueryResult>> getData(@Path("page") String page);

    @GET("page/9/quran-uthmani")
    Call<List<QueryResult>> getDataTest();

    @GET("page/{page}/quran-uthmani")
    Call<QueryResult> getArray(@Path("page") String page);
}
