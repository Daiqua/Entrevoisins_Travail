package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
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
    Button mFavoriteButton;
    @BindView(R.id.collapsing_toolbar_return_button)
    Button mReturnButton;
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
        setContentView(R.layout.activity_neighbour_details);

        //collapsing toolbar creation
        Toolbar toolbar = (Toolbar) findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setContentScrimColor(Color.RED);

        ButterKnife.bind(this);




        //TODO: simplify with genson

        neighbourPosition = getIntent().getExtras().getInt("position");

        //generate the same list than the RecyclerView of NeighbourFragment

        mApiService = DI.getNeighbourApiService();
        mNeighbours = mApiService.getNeighbours();
        mNeighbour = mNeighbours.get(neighbourPosition);

        //load the user data

        String name = mNeighbour.getName();

        //collaspsing data update
        collapsingToolbarLayout.setTitle(name);

        //cardviews data update
        Glide.with(this)
                .load(mNeighbour.getAvatarUrl())
                .centerCrop()
                .into(mAvatar);
        mName.setText(name);
        mLocalisation.setText(mNeighbour.getAddress());
        mPhone.setText(mNeighbour.getPhoneNumber());
        //mUrl.setText(mNeighbour.getUrl);
        mUrl.setText("TBD");
        mAboutMeText.setText(mNeighbour.getAboutMe());

        isFavorite = mNeighbour.getIsFavorite();

        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listNeighbourActivityIntent = new Intent(view.getContext(), ListNeighbourActivity.class);
                view.getContext().startActivity(listNeighbourActivityIntent);
            }
        });

        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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