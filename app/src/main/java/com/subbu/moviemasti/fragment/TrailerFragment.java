package com.subbu.moviemasti.fragment;

import android.content.Intent;
import android.net.Uri;
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
import com.subbu.moviemasti.adapter.ITrailerView;
import com.subbu.moviemasti.adapter.TrailerAdapter;
import com.subbu.moviemasti.entities.Movie;
import com.subbu.moviemasti.entities.Trailer;
import com.subbu.moviemasti.rest.RestMovieDbApi;
import com.subbu.moviemasti.util.ItemDecorator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by subrahmanyam on 23-12-2015.
 */
public class TrailerFragment extends Fragment implements ITrailerView {
    @Bind(R.id.trailers_recycle_view)
    RecyclerView recyclerView;
    ArrayList<Trailer> trailers = new ArrayList<>();
    private TrailerAdapter adapter;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_trailers, null);
            ButterKnife.bind(this, view);
        }

        if (savedInstanceState != null) {
            trailers = savedInstanceState.getParcelableArrayList(Constants.TRAILERS);
        } else {
            RestMovieDbApi api = new RestMovieDbApi(this);
            Bundle bundle = getArguments();
            Movie movie = bundle.getParcelable(Constants.MOVIE);
            api.getTrailers(movie.getId());
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

        Movie movie = getArguments().getParcelable(Constants.MOVIE);
        adapter = new TrailerAdapter(this, trailers, movie.getBackdropPath());
        recyclerView.addItemDecoration(new ItemDecorator(10, 5));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showTrailers(List<Trailer> youtube) {
        trailers.addAll(youtube);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClickItem(int index) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_SHARE_BASE_URL + trailers.get(index).getSource()));
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.TRAILERS, trailers);
        super.onSaveInstanceState(outState);
    }
}
