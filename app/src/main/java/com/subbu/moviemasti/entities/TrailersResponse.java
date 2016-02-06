package com.subbu.moviemasti.entities;

import java.util.List;

/**
 * Created by subrahmanyam on 20-12-2015.
 */
public class TrailersResponse {
    private List<Trailer> youtube;

    public List<Trailer> getYoutube() {
        return youtube;
    }

    public void setYoutube(List<Trailer> youtube) {
        this.youtube = youtube;
    }

    @Override
    public String toString() {
        return "TrailersResponse{" +
                "youtube=" + youtube +
                '}';
    }
}
