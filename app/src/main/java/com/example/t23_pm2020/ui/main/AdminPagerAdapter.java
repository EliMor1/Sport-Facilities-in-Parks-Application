package com.example.t23_pm2020.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.t23_pm2020.Alerts;
import com.example.t23_pm2020.Favorites;
import com.example.t23_pm2020.Options;
import com.example.t23_pm2020.R;
import com.example.t23_pm2020.allLocations;
import com.example.t23_pm2020.searchLocations;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class AdminPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_3, R.string.tab_text_4, R.string.tab_text_5};
    private final Context mContext;

    public AdminPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (position == 0) {
            searchLocations newsearchlocation = searchLocations.newInstance();
            newsearchlocation.setManager(true);
            return newsearchlocation;
        }
        else if (position == 1){
                allLocations newlocationlist = allLocations.newInstance();
                newlocationlist.setManager(true);
                return newlocationlist;
        }

        else if (position == 2){
            Favorites Favoriteslist = Favorites.newInstance();
            Favoriteslist.setManager(true);
            return Favoriteslist;
        } else if (position == 3){
            return Alerts.newInstance();
        } else
           return Options.newInstance();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {

        return 5;
    }
}