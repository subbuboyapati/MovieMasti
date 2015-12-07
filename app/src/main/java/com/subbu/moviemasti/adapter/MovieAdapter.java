package com.subbu.moviemasti.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.subbu.moviemasti.Constants;
import com.subbu.moviemasti.R;
import com.subbu.moviemasti.entities.Movie;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by subrahmanyam on 25-11-2015.
 */
public class MovieAdapter extends BaseAdapter {

    private final List<Movie> movieList;

    public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Movie movie = (Movie) getItem(position);
        String imageUrl = Constants.MOVIE_POSTER_BASE_URL + movie.getPosterPath();
        Picasso.with(parent.getContext()).load(imageUrl).
                placeholder(R.drawable.img_default).
                into(viewHolder.posterImage);
        return convertView;
    }

    public class ViewHolder {
        @Bind(R.id.poster)
        ImageView posterImage;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
