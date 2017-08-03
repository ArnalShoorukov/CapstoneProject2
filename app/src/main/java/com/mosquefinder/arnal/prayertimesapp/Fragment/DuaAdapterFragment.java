package com.mosquefinder.arnal.prayertimesapp.Fragment;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mosquefinder.arnal.prayertimesapp.R;

/**
 * Created by arnal on 7/29/17.
 */

public class DuaAdapterFragment extends FragmentStatePagerAdapter {
    /**
     * Context of the app
     */
    private Context mContext;

    public DuaAdapterFragment(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (position == 0) {
            return new AllFragment();
        } else
            return new FavorFragment();

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return mContext.getString(R.string.category_all);
        } else {
            return mContext.getString(R.string.category_favor);
        }
    }
}