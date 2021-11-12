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

    //Modified by Yoann: added a second page and use position to define what list generate
    //TODO: bug when click on the favortie list item. not the correct item is shown but item of the other list.
    //TODO: bug when deleting on favorite list. Another neighbour appears.
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