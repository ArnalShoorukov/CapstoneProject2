package com.mosquefinder.arnal.prayertimesapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.mosquefinder.arnal.prayertimesapp.Quran.Ayah;
import com.mosquefinder.arnal.prayertimesapp.Quran.Data;
import com.mosquefinder.arnal.prayertimesapp.Quran.QueryResult;
import com.mosquefinder.arnal.prayertimesapp.adapter.AyahAdapter;
import com.mosquefinder.arnal.prayertimesapp.remote.APIUtils;
import com.mosquefinder.arnal.prayertimesapp.remote.SOService;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailQuranActivity extends AppCompatActivity {
    private static final String TAG = DetailQuranActivity.class.getSimpleName();
    String numPage;
    private SOService mService;
    public static List<Ayah> ayahList = new ArrayList<>();
    private AyahAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mService = APIUtils.getSOService();
        setContentView(R.layout.activity_detail_quran);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            numPage = bundle.getString("page");

        }
        loadPage(numPage);

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
                    result.getStatus();
                    Data newData = result.getDataResult();
                    ayahList = newData.getAyahs();
                    adapter.setAyahList(ayahList);
                } else {
                    int statusCode = response.code();
                    Log.d(TAG, Integer.toString(statusCode));
                }
            }

            @Override
            public void onFailure(Call<QueryResult> call, Throwable t) {
                Toast toast = Toast.makeText(DetailQuranActivity.this, R.string.network_unavailable, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
}
