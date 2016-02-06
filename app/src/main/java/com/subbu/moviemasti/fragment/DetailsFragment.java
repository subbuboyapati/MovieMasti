package com.subbu.moviemasti.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.subbu.moviemasti.Constants;
import com.subbu.moviemasti.R;
import com.subbu.moviemasti.entities.Movie;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements IDetailsFragment {

    private static final String LOG = DetailsFragment.class.getSimpleName();
    @Bind(R.id.movie_release_date)
    TextView movieReleaseDate;
    @Bind(R.id.movie_overview)
    TextView movieDescription;
    @Bind(R.id.movie_title_description)
    TextView movieTitle;
    @Bind(R.id.rating_view)
    TextView movieRating;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Movie movie = bundle.getParcelable(Constants.MOVIE);
            if (movie != null) {
                movieReleaseDate.setText(movie.getReleaseDate());
                movieDescription.setText(movie.getOverview());
                movieTitle.setText(movie.getOriginalTitle());
                movieRating.setText(movie.getVoteAverage());
            }
        }
        return view;
    }

}
