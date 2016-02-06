package com.subbu.moviemasti.adapter;

import com.subbu.moviemasti.entities.Trailer;

import java.util.List;

/**
 * Created by subrahmanyam on 23-12-2015.
 */
public interface ITrailerView {
    void showTrailers(List<Trailer> youtube);

    void onClickItem(int index);
}
