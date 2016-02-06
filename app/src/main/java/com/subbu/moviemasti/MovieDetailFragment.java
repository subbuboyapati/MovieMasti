package com.subbu.moviemasti;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.subbu.moviemasti.data.MovieContract;
import com.subbu.moviemasti.entities.Movie;
import com.subbu.moviemasti.fragment.DetailsFragment;
import com.subbu.moviemasti.fragment.ReviewFragment;
import com.subbu.moviemasti.fragment.TrailerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {
    private static final String LOG = MovieDetailFragment.class.getSimpleName();
    @Bind(R.id.image)
    ImageView imageView;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    private View view;
    private Movie movie;

    public MovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
            ButterKnife.bind(this, view);
            init();
        }
        return view;
    }

    private void init() {
        movie = getArguments().getParcelable(Constants.MOVIE);
        String imageUrl = Constants.MOVIE_POSTER_BASE_URL + movie.getBackdropPath();
        Picasso.with(getActivity()).load(imageUrl).into(imageView);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void setupViewPager(ViewPager viewPager) {
        DetailsFragment fragment = new DetailsFragment();
        TrailerFragment trailerFragment = new TrailerFragment();
        ReviewFragment reviewFragment = new ReviewFragment();
        fragment.setArguments(getArguments());
        trailerFragment.setArguments(getArguments());
        reviewFragment.setArguments(getArguments());
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(fragment, "Details");
        adapter.addFragment(trailerFragment, "Trailers");
        adapter.addFragment(reviewFragment, "Reviews");
        viewPager.setAdapter(adapter);
    }

    @OnClick(R.id.favorite_fab)
    void onFavoriteClick() {
        Log.d(LOG, "Movie click::" + movie.getId());
        ContentValues values = new ContentValues();
        values.put(MovieContract.FavoriteMovieEntry.ID, movie.getId());
        values.put(MovieContract.FavoriteMovieEntry.TITLE, movie.getTitle());
        values.put(MovieContract.FavoriteMovieEntry.POSTER, movie.getPosterPath());
        values.put(MovieContract.FavoriteMovieEntry.POPULARITY, movie.getPopularity());
        values.put(MovieContract.FavoriteMovieEntry.VOTE_AVERAGE, movie.getVoteAverage());
        values.put(MovieContract.FavoriteMovieEntry.VOTE_COUNT, movie.getVoteCount());
        values.put(MovieContract.FavoriteMovieEntry.RELEASE_DATE, movie.getReleaseDate());
        values.put(MovieContract.FavoriteMovieEntry.OVERVIEW, movie.getOverview());
        values.put(MovieContract.FavoriteMovieEntry.ORIGINAL_TITLE, movie.getOriginalTitle());
        values.put(MovieContract.FavoriteMovieEntry.BACKDROP, movie.getBackdropPath());
        getContext().getContentResolver().insert(MovieContract.FavoriteMovieEntry.CONTENT_URI, values);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
