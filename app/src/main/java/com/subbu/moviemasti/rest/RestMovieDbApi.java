package com.subbu.moviemasti.rest;

import android.util.Log;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;
import com.subbu.moviemasti.BuildConfig;
import com.subbu.moviemasti.Constants;
import com.subbu.moviemasti.entities.MovieResponse;
import com.subbu.moviemasti.fragment.IDiscoveryFragment;

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
    private final IDiscoveryFragment discoveryFragment;
    private IRestMovieDbApi restDataApi;

    public RestMovieDbApi(IDiscoveryFragment discoveryFragment) {
        this.discoveryFragment = discoveryFragment;
        initRestAdapter();
    }

    private void initRestAdapter() {
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.networkInterceptors().add(new StethoInterceptor());
        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(Constants.API_BASE_URL).
                setClient(new OkClient(httpClient))/*.
                setLogLevel(RestAdapter.LogLevel.FULL).setLog(RestAdapter.Log.NONE)*/;
        restDataApi = builder.build().create(IRestMovieDbApi.class);
    }

    public void getMoviesList(String preference) {
        restDataApi.getSortByMovies(preference, 50, BuildConfig.API_KEY, movieListCallback);
    }

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
}
