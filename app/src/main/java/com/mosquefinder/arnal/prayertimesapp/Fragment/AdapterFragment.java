package com.mosquefinder.arnal.prayertimesapp.Fragment;


import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mosquefinder.arnal.prayertimesapp.Fragment.CompassFragment;
import com.mosquefinder.arnal.prayertimesapp.Fragment.MapsFragment;
import com.mosquefinder.arnal.prayertimesapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdapterFragment extends FragmentStatePagerAdapter {
   /** Context of the app */
    private Context mContext;
    public AdapterFragment(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (position == 0) {
            return new CompassFragment();
        } else
            return new MapsFragment();

    }
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(position == 0){
            return mContext.getString(R.string.category_compass);
        }else {
            return mContext.getString(R.string.category_map);
        }
    }
}
