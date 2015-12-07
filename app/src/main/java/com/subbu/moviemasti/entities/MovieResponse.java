package com.subbu.moviemasti.entities;


import java.util.List;

/**
 * Created by subrahmanyam on 25-11-2015.
 */
public class MovieResponse {
    private String page;
    private List<Movie> results;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "page='" + page + '\'' +
                ", results=" + results +
                '}';
    }
}
