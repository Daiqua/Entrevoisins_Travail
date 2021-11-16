package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

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
    @BindView(R.id.activity_neighbour_details_name)
    TextView mName;
    @BindView(R.id.activity_neighbour_details_localisation)
    TextView mLocalisation;
    @BindView(R.id.activity_neighbour_details_phone)
    TextView mPhone;
    @BindView(R.id.activity_neighbour_details_url)
    TextView mUrl;
    @BindView(R.id.activity_neighbour_details_description_about_me)
    TextView mAboutMeTitle;
    @BindView(R.id.activity_neighbour_details_description_text)
    TextView mAboutMeText;
    @BindView(R.id.collapsing_toolbar_favorite_button)
    FloatingActionButton mFavoriteButton;
    @BindView(R.id.collapsing_toolbar_avatar)
    ImageView mAvatar;

    private int neighbourPosition;
    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private Neighbour mNeighbour;

    //TODO: added to manage list choice
    private int positionForList;//putExtra name: list_position


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_details_collapsingtoolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);

        //collapsing toolbar creation
        Toolbar toolbar = findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);

        //TODO: review the back arrow thickness
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        neighbourPosition = getIntent().getExtras().getInt("position");

        //TODO: added to manage list choice
        positionForList = getIntent().getExtras().getInt("list_position");

        //TODO: faire le tri
        //TODO: do not generate a new list
        //generate the same list than the RecyclerView of NeighbourFragment
        mApiService = DI.getNeighbourApiService();

        //TODO: added to manage list choice
        if (positionForList==0){
            mNeighbours = mApiService.getNeighbours();
        }else{
            mNeighbours=mApiService.getFavoriteNeighbours();
        }


        //TODO prendre la bonne liste - Créer un énum et la passer dans l'intent
        mNeighbour = mNeighbours.get(neighbourPosition);


        String name = mNeighbour.getName();
        collapsingToolbarLayout.setTitle(name);

        //cardviews data update
        Glide.with(this)
                .load(mNeighbour.getAvatarUrl())
                .centerCrop()
                .into(mAvatar);
        mName.setText(name);
        mLocalisation.setText(mNeighbour.getAddress());
        mPhone.setText(mNeighbour.getPhoneNumber());
        //TODO: créer ou récupérer les Url
        //mUrl.setText(mNeighbour.getUrl);
        mUrl.setText("TBD");
        mAboutMeText.setText(mNeighbour.getAboutMe());
        //load the favorite button picture
        setFavoriteIconDrawable(mNeighbour.getIsFavorite());

        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiService.setFavorite(mNeighbour);
                setFavoriteIconDrawable(mNeighbour.getIsFavorite());
            }
        });
    }

    //Define favorite button appearance
    void setFavoriteIconDrawable(boolean isFavorite){
        Drawable favoriteOn = getResources().getDrawable(R.drawable.baseline_star_rate_yellow_700_48dp);
        Drawable favoriteOff = getResources().getDrawable(R.drawable.round_star_rate_grey_600_48dp);
        if (isFavorite){
            mFavoriteButton.setImageDrawable(favoriteOn);
        }else{
            mFavoriteButton.setImageDrawable(favoriteOff);
        }
    }
}