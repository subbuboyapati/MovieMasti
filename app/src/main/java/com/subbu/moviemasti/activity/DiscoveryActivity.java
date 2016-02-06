package com.subbu.moviemasti.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.subbu.moviemasti.Constants;
import com.subbu.moviemasti.MovieDetailFragment;
import com.subbu.moviemasti.R;
import com.subbu.moviemasti.entities.Movie;
import com.subbu.moviemasti.fragment.DetailsFragment;
import com.subbu.moviemasti.fragment.DiscoveryFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DiscoveryActivity extends AppCompatActivity implements DiscoveryFragment.Callback {
    private static final String DETAILS_FRAGMENT_TAG = "DetailsFragment";
    @Bind(R.id.discovery_toolbar)
    Toolbar toolbar;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        if (findViewById(R.id.movie_details_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.movie_details_container,
                        new DetailsFragment(), DETAILS_FRAGMENT_TAG).commit();
            }
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_discovery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        String[] filterValues = getResources().getStringArray(R.array.pref_sort_by_values);
        switch (item.getItemId()) {
            case R.id.action_popularity:
                sharedPrefs.edit().putString(getString(R.string.pref), filterValues[0]).commit();
                break;
            case R.id.action_top_rated:
                sharedPrefs.edit().putString(getString(R.string.pref), filterValues[1]).commit();
                break;
            case R.id.action_favorite:
                sharedPrefs.edit().putString(getString(R.string.pref), filterValues[2]).commit();
                break;
        }
        finish();
        startActivity(getIntent());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.MOVIE, movie);
        if (mTwoPane) {
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.movie_details_container, fragment).commit();
        } else {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
