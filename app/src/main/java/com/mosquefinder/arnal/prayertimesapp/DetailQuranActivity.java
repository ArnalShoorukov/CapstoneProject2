package com.mosquefinder.arnal.prayertimesapp;

import android.icu.text.LocaleDisplayNames;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.mosquefinder.arnal.prayertimesapp.Quran.Ayah;
import com.mosquefinder.arnal.prayertimesapp.Quran.Data;
import com.mosquefinder.arnal.prayertimesapp.Quran.QueryResult;
import com.mosquefinder.arnal.prayertimesapp.adapter.AyahAdapter;
import com.mosquefinder.arnal.prayertimesapp.remote.APIUtils;
import com.mosquefinder.arnal.prayertimesapp.remote.SOService;

import net.alqs.iclib.salat.Times;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailQuranActivity extends AppCompatActivity {
    private static final String TAG = DetailQuranActivity.class.getSimpleName();
    TextView nameSurrah;
    String numPage;
    private SOService mService;
    public static List<Ayah> ayahList = new ArrayList<>();
    private AyahAdapter adapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mService = APIUtils.getSOService();
        setContentView(R.layout.activity_detail_quran);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            numPage = bundle.getString("page");

        }
        //nameSurrah = (TextView)findViewById(R.id.nameSurah);
        loadPage(numPage);

        for (int l = 0; l < ayahList.size(); l++) {
            Log.d("PageNum", ayahList.get(l).getText());
        }
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler_view_ayah);
        adapter = new AyahAdapter(this, ayahList);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        recycler.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(itemDecoration);

    }

    private void loadPage(String page) {

        mService.getArray(page).enqueue(new Callback<QueryResult>() {
            @Override
            public void onResponse(Call<QueryResult> call, Response<QueryResult> response) {
                if (response.isSuccessful()) {
                    QueryResult result = response.body();
                    String name = null;
                    result.getStatus();
                    Data newData = result.getDataResult();
                    ayahList = newData.getAyahs();
                    for (int i = 0; i < ayahList.size(); i++) {
                        name = ayahList.get(i).getSurah().getEnglishName();
                        Log.d(TAG, ayahList.get(i).getText());
                    }

                    Log.d(TAG, name);
                    adapter.setAyahList(ayahList);
                    // nameSurrah.setText(name);
                    //  Log.d(TAG,  Integer.toString(newData.getAyahs().toString());


                } else {
                    int statusCode = response.code();
                    Log.d(TAG, Integer.toString(statusCode));
                }
            }

            @Override
            public void onFailure(Call<QueryResult> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });

    }
}
