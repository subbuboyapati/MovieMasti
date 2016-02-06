package com.subbu.moviemasti.fragment;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.subbu.moviemasti.Constants;
import com.subbu.moviemasti.R;
import com.subbu.moviemasti.adapter.MovieAdapter;
import com.subbu.moviemasti.data.MovieContract;
import com.subbu.moviemasti.data.MovieProvider;
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
    RecyclerView gridView;
    private MovieResponse movieResponse;
    private ArrayList<Movie> movieList;
    private int currentPage = 1;
    private RestMovieDbApi api;
    private String currentPreference;
    private boolean loading;
    private MovieAdapter adapter;
    private int selectedPosition;
    private MovieAdapter.onRecyclerViewItemClickListener itemClickListener = new MovieAdapter.onRecyclerViewItemClickListener() {
        @Override
        public void onItemClickListener(View view, int position) {
            Movie movie = movieList.get(position);
            selectedPosition = position;
            ((Callback) getActivity()).onItemSelected(movie);
        }
    };
    private View view;
    private GridLayoutManager manager;
    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            totalItemCount = manager.getItemCount();
            lastVisibleItem = manager.findLastVisibleItemPosition();
            if (!loading
                    && totalItemCount <= (lastVisibleItem + visibleThreshold)
                    && !currentPreference.equals(getString(R.string.favorite))) {
                currentPage++;
                loadMore();
                loading = true;
            }
        }
    };

    public DiscoveryFragment() {
        // Required empty public constructor
    }

    private void loadMore() {
        api.getMoviesList(currentPreference, currentPage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_discovery, container, false);
            ButterKnife.bind(this, view);
            int cols = (int) getResources().getInteger(R.integer.number_of_cols);
            manager = new GridLayoutManager(getActivity(), cols);
            gridView.setLayoutManager(manager);
            api = new RestMovieDbApi(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            currentPreference = preferences.getString(getString(R.string.pref),
                    getString(R.string.pref_sort_by_default));
            gridView.addOnScrollListener(scrollListener);
            if (savedInstanceState != null) {
                movieList = savedInstanceState.getParcelableArrayList(Constants.MOVIE_LIST);
                selectedPosition = savedInstanceState.getInt(Constants.SELECTED_POSITION);
            } else {
                movieList = new ArrayList<>();
                if (currentPreference.equals(getString(R.string.favorite))) {
                    MovieProvider cursor = (MovieProvider) (getActivity().getContentResolver()
                            .acquireContentProviderClient(MovieContract.FavoriteMovieEntry.CONTENT_URI))
                            .getLocalContentProvider();
                    Cursor cursor1 = cursor.query(MovieContract.FavoriteMovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
                    cursor1.moveToFirst();
                    movieList = getMovieListFromCursor(cursor1);
                    DatabaseUtils.dumpCursor(cursor1);
                } else {
                    api.getMoviesList(currentPreference, currentPage);
                }
            }
            adapter = new MovieAdapter(movieList);
            gridView.setAdapter(adapter);
            gridView.smoothScrollToPosition(selectedPosition);
            adapter.setOnItemClickListener(itemClickListener);
        }
        return view;
    }

    private ArrayList<Movie> getMovieListFromCursor(Cursor cursor1) {
        ArrayList<Movie> movieList = new ArrayList<>();
        do {
            int id = cursor1.getInt(0);
            String movieTitle = cursor1.getString(1);
            String poster = cursor1.getString(2);
            Movie movie = new Movie(id, movieTitle, poster);
            movie.setPopularity(cursor1.getString(3));
            movie.setVoteAverage(cursor1.getString(4));
            movie.setVoteCount(Integer.parseInt(cursor1.getString(5)));
            movie.setReleaseDate(cursor1.getString(6));
            movie.setOverview(cursor1.getString(7));
            movie.setOriginalTitle(cursor1.getString(8));
            movie.setBackdropPath(cursor1.getString(9));
            movieList.add(movie);
        } while (cursor1.moveToNext());
        return movieList;
    }

    @Override
    public void showMovieList(MovieResponse movieResponse) {
        this.movieResponse = movieResponse;
        movieList.addAll(movieResponse.getResults());
        adapter.notifyDataSetChanged();
        loading = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.MOVIE_LIST, movieList);
        outState.putInt(Constants.SELECTED_POSITION, selectedPosition);
    }

    public interface Callback {
        void onItemSelected(Movie movie);
    }
}
