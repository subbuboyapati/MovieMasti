package com.subbu.moviemasti.rest;

import android.util.Log;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;
import com.subbu.moviemasti.BuildConfig;
import com.subbu.moviemasti.Constants;
import com.subbu.moviemasti.adapter.ITrailerView;
import com.subbu.moviemasti.entities.MovieResponse;
import com.subbu.moviemasti.entities.ReviewResponse;
import com.subbu.moviemasti.entities.TrailersResponse;
import com.subbu.moviemasti.fragment.IDetailsFragment;
import com.subbu.moviemasti.fragment.IDiscoveryFragment;
import com.subbu.moviemasti.fragment.IReviewView;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by subrahmanyam on 25-11-2015.
 */
public class RestMovieDbApi {

    private static final String LOG = RestMovieDbApi.class.getSimpleName();
    private IReviewView reviewView;
    private ITrailerView trailerFragment;
    private IDiscoveryFragment discoveryFragment;
    Callback<MovieResponse> movieListCallback = new Callback<MovieResponse>() {
        @Override
        public void success(MovieResponse movieResponse, Response response) {
            Log.d(LOG, response.getUrl());
            discoveryFragment.showMovieList(movieResponse);
        }

        @Override
        public void failure(RetrofitError error) {
            Log.d(LOG, error.getUrl());
            error.printStackTrace();
        }
    };
    private IDetailsFragment detailsFragment;
    private IRestMovieDbApi restDataApi;
    private Callback<TrailersResponse> trailerCallback = new Callback<TrailersResponse>() {
        @Override
        public void success(TrailersResponse trailersResponse, Response response) {
            Log.d(LOG, response.getUrl());
            Log.d(LOG, trailersResponse.toString());
            trailerFragment.showTrailers(trailersResponse.getYoutube());
        }

        @Override
        public void failure(RetrofitError error) {
            Log.d(LOG, error.getUrl());
            error.printStackTrace();
        }
    };
    private Callback<ReviewResponse> reviewCallback = new Callback<ReviewResponse>() {
        @Override
        public void success(ReviewResponse reviewResponse, Response response) {
            Log.d(LOG, response.getUrl());
            Log.d(LOG, reviewResponse.getResults().size() + ">>");
            reviewView.showReviews(reviewResponse);
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e(LOG, error.getUrl());
            error.printStackTrace();
        }
    };

    public RestMovieDbApi(IDiscoveryFragment discoveryFragment) {
        this.discoveryFragment = discoveryFragment;
        initRestAdapter();
    }

    public RestMovieDbApi(IDetailsFragment detailsFragment) {
        this.detailsFragment = detailsFragment;
        initRestAdapter();
    }

    public RestMovieDbApi(ITrailerView trailerFragment) {
        this.trailerFragment = trailerFragment;
        initRestAdapter();
    }

    public RestMovieDbApi(IReviewView reviewView) {
        this.reviewView = reviewView;
        initRestAdapter();
    }

    private void initRestAdapter() {
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.networkInterceptors().add(new StethoInterceptor());
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(Constants.API_BASE_URL)
                .setClient(new OkClient(httpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL).setLog(RestAdapter.Log.NONE);
        restDataApi = builder.build().create(IRestMovieDbApi.class);
    }

    public void getMoviesList(String preference, int pageNumber) {
        restDataApi.getSortByMovies(preference, 50, BuildConfig.API_KEY,
                pageNumber, movieListCallback);
    }

    public void getTrailers(int movieId) {
        Log.d(LOG, movieId + ">>>");
        restDataApi.getTrailers(movieId, BuildConfig.API_KEY, trailerCallback);
    }

    public void getReviews(int movieId) {
        restDataApi.getReviews(movieId, BuildConfig.API_KEY, reviewCallback);
    }
}
