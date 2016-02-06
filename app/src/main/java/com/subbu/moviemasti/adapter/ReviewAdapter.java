package com.subbu.moviemasti.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.subbu.moviemasti.R;
import com.subbu.moviemasti.entities.Review;
import com.subbu.moviemasti.fragment.IReviewView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by subrahmanyam on 24-12-2015.
 */
public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Review> results;

    public ReviewAdapter(IReviewView reviewFragment, List<Review> results) {
        this.results = results;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                view = View.inflate(parent.getContext(), R.layout.list_item_empty_view, null);
                viewHolder = new EmptyViewHolder(view);
                break;
            case 1:
                view = View.inflate(parent.getContext(), R.layout.review_list_item, null);
                viewHolder = new ViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 0:
                EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
                emptyViewHolder.emptyView.setText(((EmptyViewHolder) holder).emptyView.getResources().getString(R.string.no_trailers));
                break;
            case 1:
                Review review = results.get(position);
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.userName.setText(review.getAuthor());
                viewHolder.review.setText(review.getContent());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return results.size() > 0 ? results.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        return results.size() == 0 ? 0 : 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.review_user)
        TextView userName;
        @Bind(R.id.review_review)
        TextView review;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
