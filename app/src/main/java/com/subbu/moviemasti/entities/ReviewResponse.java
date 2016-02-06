package com.subbu.moviemasti.entities;

import java.util.List;

/**
 * Created by subrahmanyam on 21-12-2015.
 */
public class ReviewResponse {
    List<Review> results;

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ReviewResponse{" +
                "results=" + results +
                '}';
    }
}
