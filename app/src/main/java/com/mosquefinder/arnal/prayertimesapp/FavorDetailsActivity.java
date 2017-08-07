package com.mosquefinder.arnal.prayertimesapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.mosquefinder.arnal.prayertimesapp.Fragment.FavouriteDetailFragment;


public class FavorDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor_details);
        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putParcelable(FavouriteDetailFragment.DUA_URI, getIntent().getData());

            FavouriteDetailFragment fragment = new FavouriteDetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction().add(R.id.activity_favor_details, fragment).commit();
        }
    }

}
