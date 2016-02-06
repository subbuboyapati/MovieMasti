package com.subbu.moviemasti.rest;


import com.subbu.moviemasti.entities.MovieResponse;
import com.subbu.moviemasti.entities.ReviewResponse;
import com.subbu.moviemasti.entities.TrailersResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by subrahmanyam on 25-11-2015.
 */
public interface IRestMovieDbApi {
    @GET("/discover/movie")
    void getSortByMovies(@Query("sort_by") String sortBy, @Query("vote_count.gte") int count,
                         @Query("api_key") String apiKey, @Query("page") int page,
                         Callback<MovieResponse> callback);

    @GET("/movie/{movieId}/trailers")
    void getTrailers(@Path("movieId") int movieId, @Query("api_key") String apiKey,
                     Callback<TrailersResponse> callback);

    @GET("/movie/{movieId}/reviews")
    void getReviews(@Path("movieId") int movieId, @Query("api_key") String apiKey,
                    Callback<ReviewResponse> callback);
}
