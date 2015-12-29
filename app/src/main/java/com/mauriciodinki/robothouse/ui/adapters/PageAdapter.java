package com.mauriciodinki.robothouse.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mauriciodinki.robothouse.ui.fragments.MovementFragment;

/**
 * Created by mauriciodinki on 29/12/15.
 */
public class PageAdapter extends FragmentStatePagerAdapter {

    int mNumTabs;

    public PageAdapter(FragmentManager fm, int mNumTabs) {
        super(fm);
        this.mNumTabs = mNumTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MovementFragment movementFragment = new MovementFragment();
                return  movementFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumTabs;
    }
}
