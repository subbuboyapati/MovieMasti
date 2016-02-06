package com.subbu.moviemasti.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.subbu.moviemasti.Constants;
import com.subbu.moviemasti.R;
import com.subbu.moviemasti.adapter.ReviewAdapter;
import com.subbu.moviemasti.entities.Movie;
import com.subbu.moviemasti.entities.Review;
import com.subbu.moviemasti.entities.ReviewResponse;
import com.subbu.moviemasti.rest.RestMovieDbApi;
import com.subbu.moviemasti.util.ItemDecorator;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by subrahmanyam on 23-12-2015.
 */
public class ReviewFragment extends Fragment implements IReviewView {
    @Bind(R.id.trailers_recycle_view)
    RecyclerView recyclerView;
    private ReviewAdapter adapter;
    private ArrayList<Review> reviews = new ArrayList<>();
    private View view;
    private Movie movie;
    private RestMovieDbApi mRestMovieDbApi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_reviews, null);
            ButterKnife.bind(this, view);
            init();
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(manager);
            recyclerView.addItemDecoration(new ItemDecorator(10, 5));
            if (savedInstanceState != null) {
                reviews = savedInstanceState.getParcelableArrayList(Constants.REVIEWS);
            } else {
                mRestMovieDbApi.getReviews(movie.getId());
            }
            adapter = new ReviewAdapter(this, reviews);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    private void init() {
        Bundle bundle = getArguments();
        movie = bundle.getParcelable(Constants.MOVIE);
        mRestMovieDbApi = new RestMovieDbApi(this);
    }

    @Override
    public void showReviews(ReviewResponse results) {
        reviews.addAll(results.getResults());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.REVIEWS, reviews);
        super.onSaveInstanceState(outState);
    }
}
