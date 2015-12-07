package com.subbu.moviemasti.rest;


import com.subbu.moviemasti.entities.MovieResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by subrahmanyam on 25-11-2015.
 */
public interface IRestMovieDbApi {
    @GET("/movie")
    void getSortByMovies(@Query("sort_by") String sortBy, @Query("vote_count.gte") int count,@Query("api_key") String apiKey,
                         Callback<MovieResponse> callback);
}
