package com.example.pocket_kitchen.ui.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.pocket_kitchen.ui.fragments.Honey_tip_MainFragment;
import com.example.pocket_kitchen.ui.fragments.My_Recipe_MainFragment;

public class ContentsPagerAdapter extends FragmentStatePagerAdapter {

    private int mPageCount;


    public ContentsPagerAdapter(FragmentManager fm, int pageCount) {
        super(fm);
        this.mPageCount = pageCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new My_Recipe_MainFragment();
            case 1:
                return new Honey_tip_MainFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mPageCount;
    }
}