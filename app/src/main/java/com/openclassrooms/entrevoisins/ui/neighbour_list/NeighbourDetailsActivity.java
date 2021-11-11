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


    private boolean isFavorite=false;

    private int neighbourPosition;
    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private Neighbour mNeighbour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_details_collapsingtoolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);

        //collapsing toolbar creation
        Toolbar toolbar = findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);

        //added by Yoann to go back home
        //TODO: review the back arrow thickness
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        ButterKnife.bind(this);

        //TODO: simplify with genson

        neighbourPosition = getIntent().getExtras().getInt("position");

        //TODO: faire le tri
        //generate the same list than the RecyclerView of NeighbourFragment
        mApiService = DI.getNeighbourApiService();
        mNeighbours = mApiService.getNeighbours();
        mNeighbour = mNeighbours.get(neighbourPosition);

        //load the user data
        String name = mNeighbour.getName();

       // mFavoriteButton.bringToFront();

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



        isFavorite = mNeighbour.getIsFavorite();

        Drawable favoriteOn = getResources().getDrawable(R.drawable.baseline_star_rate_yellow_700_48dp);
        Drawable favoriteOff = getResources().getDrawable(R.drawable.round_star_rate_grey_600_48dp);

        if (isFavorite){
            mFavoriteButton.setImageDrawable(favoriteOn);
        }else{
            mFavoriteButton.setImageDrawable(favoriteOff);
        }

        //TODO: déplacer le bouton + mettre une étoile + gérer la méthode via l'API

        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: faire appel à une méthode dans l'API

                if (isFavorite) {
                    mNeighbour.setIsFavorite(false);
                } else {
                    mNeighbour.setIsFavorite(true);
                }
                isFavorite = mNeighbour.getIsFavorite();
                Toast.makeText(view.getContext(), "Favoris set to:" + isFavorite, Toast.LENGTH_SHORT).show();
            }
        });

    }

}