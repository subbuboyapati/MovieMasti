package com.subbu.moviemasti.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.subbu.moviemasti.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by subrahmanyam on 06-02-2016, 03:24 PM.
 */
public class EmptyViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.empty_view)
    TextView emptyView;

    public EmptyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
