package com.subbu.moviemasti.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.subbu.moviemasti.Constants;
import com.subbu.moviemasti.R;
import com.subbu.moviemasti.entities.Movie;
import com.subbu.moviemasti.fragment.DetailsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @Bind(R.id.image)
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(getIntent().getExtras());
        Movie movie = getIntent().getParcelableExtra(Constants.MOVIE);
        String imageUrl = Constants.MOVIE_POSTER_BASE_URL +movie.getBackdropPath();
        Picasso.with(this).load(imageUrl).into(imageView);
        getSupportActionBar().setTitle(movie.getOriginalTitle());
        getSupportFragmentManager().beginTransaction().add(R.id.details_container, fragment).commit();
    }
}
