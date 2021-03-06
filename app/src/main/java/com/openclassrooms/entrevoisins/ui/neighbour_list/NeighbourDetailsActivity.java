package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NeighbourDetailsActivity extends AppCompatActivity {

    // UI Components
    @BindView(R.id.activity_neighbour_cardviews_name)
    TextView mName;
    @BindView(R.id.activity_neighbour_cardviews_localisation)
    TextView mLocalisation;
    @BindView(R.id.activity_neighbour_cardviews_phone)
    TextView mPhone;
    @BindView(R.id.activity_neighbour_cardviews_url)
    TextView mUrl;
    @BindView(R.id.activity_neighbour_cardviews_description_about_me)
    TextView mAboutMeTitle;
    @BindView(R.id.activity_neighbour_cardviews_description_text)
    TextView mAboutMeText;
    @BindView(R.id.activity_details_neighbour_favorite_button)
    FloatingActionButton mFavoriteButton;
    @BindView(R.id.activity_details_neighbour_avatar)
    ImageView mAvatar;
    @BindView(R.id.activity_details_neighbour_collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.activity_details_neighbour_collapsing_toolbar)
    Toolbar toolbar;

    /**
     * neighbourPosition will be fed with position of the item from RecyclerView
     * mApiService and mNeighbours will be used to instantiate the same neighbours list which own the item clicked
     * positionForList will be fed with the list_position (favorite list or not) from RecyclerView
     */
    private int neighbourPosition;
    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private Neighbour mNeighbour;
    private int positionForList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadTheView();
        loadAndDisplayNeighbourData();

        /** click listener on favorite FAB to switch between true/false and update FAB picture */
        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiService.setFavorite(mNeighbour);
                setFavoriteIconDrawable(mNeighbour.getIsFavorite());
            }
        });
    }
    /** @param isFavorite : used to choose the appropriate icon for favorite FAB */
    private void setFavoriteIconDrawable(boolean isFavorite){
        Drawable favoriteOn = getResources().getDrawable(R.drawable.baseline_star_rate_yellow_700_48dp);
        Drawable favoriteOff = getResources().getDrawable(R.drawable.round_star_rate_grey_600_48dp);
        if (isFavorite){
            mFavoriteButton.setImageDrawable(favoriteOn);
        }else{
            mFavoriteButton.setImageDrawable(favoriteOff);
        }
    }

    private void loadTheView(){
        setContentView(R.layout.activity_details_neighbour);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//display the back arrow on the collapsing toolbar

    }

    private void loadAndDisplayNeighbourData(){
        //feed parameter to define what neighbours was clicked
        neighbourPosition = getIntent().getExtras().getInt("position");
        positionForList = getIntent().getExtras().getInt("list_position");
        //generate the list
        mApiService = DI.getNeighbourApiService();
        mNeighbours = mApiService.getNeighbours(positionForList);
        //generate the neighbour
        mNeighbour = mNeighbours.get(neighbourPosition);
        //update the layout with neighbour data
        String name = mNeighbour.getName();
        collapsingToolbarLayout.setTitle(name);
        mName.setText(name);
        Glide.with(this)
                .load(mNeighbour.getAvatarUrl())
                .centerCrop()
                .into(mAvatar);
        mLocalisation.setText(mNeighbour.getAddress());
        mPhone.setText(mNeighbour.getPhoneNumber());
        mUrl.setText(mNeighbour.getUrl());
        mAboutMeText.setText(mNeighbour.getAboutMe());
        //favorite button picture
        setFavoriteIconDrawable(mNeighbour.getIsFavorite());
    }
}