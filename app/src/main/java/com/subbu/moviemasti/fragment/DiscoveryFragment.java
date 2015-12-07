package com.subbu.moviemasti.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.subbu.moviemasti.Constants;
import com.subbu.moviemasti.R;
import com.subbu.moviemasti.activity.DetailsActivity;
import com.subbu.moviemasti.adapter.MovieAdapter;
import com.subbu.moviemasti.entities.Movie;
import com.subbu.moviemasti.entities.MovieResponse;
import com.subbu.moviemasti.rest.RestMovieDbApi;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoveryFragment extends Fragment implements IDiscoveryFragment {

    private static final String LOG = DiscoveryFragment.class.getSimpleName();
    @Bind(R.id.gridView)
    GridView gridView;
    private MovieResponse movieResponse;
    private ArrayList<Movie> movieList;

    public DiscoveryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);
        ButterKnife.bind(this, view);
        RestMovieDbApi api = new RestMovieDbApi(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String preference = preferences.getString(getString(R.string.pref),
                getString(R.string.pref_sort_by_default));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailsActivity = new Intent(getActivity(), DetailsActivity.class);
                Bundle bundle = new Bundle();
                Movie movie = movieList.get(position);
                bundle.putParcelable(Constants.MOVIE, movie);
                detailsActivity.putExtras(bundle);
                startActivity(detailsActivity);
            }
        });
        if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelableArrayList(Constants.MOVIE_LIST);
            MovieAdapter adapter = new MovieAdapter(movieList);
            gridView.setAdapter(adapter);
        } else {
            api.getMoviesList(preference);
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
        }
    }

    @Override
    public void showMovieList(MovieResponse movieResponse) {
        this.movieResponse = movieResponse;
        movieList = (ArrayList<Movie>) movieResponse.getResults();
        MovieAdapter adapter = new MovieAdapter(movieResponse.getResults());
        gridView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.MOVIE_LIST, movieList);
    }
}
