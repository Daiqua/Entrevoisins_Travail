package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;


public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    static Fragment mFragment;

    public ListNeighbourPagerAdapter(FragmentManager fm) {super(fm);}


    /**
     * getItem is called to instantiate the fragment for the given page.
     * @param position
     * @return
     */
    @Override

    //Modified by Yoann: added a second page and if condition to define what page to show
    public Fragment getItem(int position) {
        mFragment = NeighbourFragment.newInstance();
        return mFragment;

        /*if(position==0)
            {
                mFragment = ListNeighbourActivity.getNeighbourFragment();
                return mFragment;
        }else if (position==1)
        {
            mFragment = ListNeighbourActivity.getFavoritesFragment();
            return mFragment;
        }else
            {return mFragment;}*/

    }

    /**
     * get the number of pages
     * @return
     */
    @Override
    public int getCount() {
        return (2); //pourquoi des parentheses?
    }

}