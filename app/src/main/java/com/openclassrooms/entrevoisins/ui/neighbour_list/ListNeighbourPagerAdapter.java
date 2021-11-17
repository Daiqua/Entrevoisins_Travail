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
     * @param position : used to define what fragment to show
     * @return
     */
    @Override

    public Fragment getItem(int position) {
       mFragment = NeighbourFragment.newInstance(position);
        return mFragment;
    }

    /**
     * get the number of pages
     * @return
     */
    @Override
    public int getCount() {return 2;}

}