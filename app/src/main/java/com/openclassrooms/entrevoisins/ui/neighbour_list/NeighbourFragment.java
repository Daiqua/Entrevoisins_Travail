package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class NeighbourFragment extends Fragment {
    /**
     * Only this fragment will be used to generate neighbours tab and favorite neighbours tab
     * mPosition & KEY_POSITION will be used to tag the fragment. This will be used during test
     * mPosition will be used to generate the appropriate list of neighbours
     */
    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private RecyclerView mRecyclerView;
    private int mPosition;
    private static String KEY_POSITION = "position" ;



    /**
     * Create and return a new instance
     * @return @{@link NeighbourFragment}
     * @param position: coming from the PagerAdapter
     */

    public static NeighbourFragment newInstance(int position) {
        NeighbourFragment fragment = new NeighbourFragment();


        //to manage the different pages
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();

        //to manage the different pages
        Bundle bundle=getArguments();
        mPosition = bundle.getInt(KEY_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        //to tag the fragment depending on the tab
        if(mPosition == 0){
            view.setContentDescription("firstPage");
        }else{
            view.setContentDescription("secondPage");
        }

        return view;
    }

    /**
     * Init the List of neighbours
     * positionForList will be used to indicate the list from where the item is coming.
     * see the MyNeighbourRecyclerViewAdapter
     * mPosition enable to indicate the tab displayed to generate the appropriate list
     */
    private void initList() {
        mNeighbours = mApiService.getNeighbours(mPosition);
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mNeighbours, mPosition));
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        mApiService.deleteNeighbour(event.neighbour);
        initList();
    }

}

