package com.subbu.moviemasti.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.subbu.moviemasti.Constants;
import com.subbu.moviemasti.MovieDetailFragment;
import com.subbu.moviemasti.R;
import com.subbu.moviemasti.entities.Movie;

import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Movie movie = getIntent().getParcelableExtra(Constants.MOVIE);
        if (savedInstanceState == null) {
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.movie_details_container,
                    fragment).commit();
        }
    }
}
