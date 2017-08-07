package com.mosquefinder.arnal.prayertimesapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.mosquefinder.arnal.prayertimesapp.Fragment.DuaAdapterFragment;
import com.mosquefinder.arnal.prayertimesapp.data.Dua;

import java.util.ArrayList;
import java.util.List;

public class DuaActivity extends AppCompatActivity {

    public static List<Dua> titleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dua2);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_dua);

        // Create an adapter that knows which fragment should be shown on each page

        DuaAdapterFragment adapter = new DuaAdapterFragment(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_dua);

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(viewPager);

    }
}
