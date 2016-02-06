package com.subbu.moviemasti.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.subbu.moviemasti.Constants;
import com.subbu.moviemasti.R;
import com.subbu.moviemasti.entities.Trailer;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by subrahmanyam on 23-12-2015.
 */
public class TrailerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Trailer> trailers;
    private final String logo;
    private final ITrailerView trailerFragment;

    public TrailerAdapter(ITrailerView trailerFragment, List<Trailer> trailers, String backdropPath) {
        this.trailers = trailers;
        logo = backdropPath;
        this.trailerFragment = trailerFragment;
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
                view = View.inflate(parent.getContext(), R.layout.trailer_list_item, null);
                viewHolder = new ViewHolder(view);
                break;
            default:
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
                ViewHolder viewHolder = (ViewHolder) holder;
                final Trailer trailer = trailers.get(position);
                viewHolder.trailerTitle.setText(trailer.getName());
                if (logo != null) {
                    String imageUrl = Constants.YOUTUBE_BASE_URL + trailer.getSource() + "/mqdefault.jpg";
                    Picasso.with(viewHolder.trailerLogo.getContext()).load(imageUrl).error(R.drawable.img_default).into(viewHolder.trailerLogo);
                }
                break;
        }

    }

    @Override
    public int getItemCount() {
        return trailers.size() > 0 ? trailers.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        return trailers.size() > 0 ? 1 : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.trailer_logo)
        ImageView trailerLogo;
        @Bind(R.id.trailer_title)
        TextView trailerTitle;
        @Bind(R.id.trailer_share)
        ImageView trailerShare;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            trailerShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = Constants.YOUTUBE_SHARE_BASE_URL + trailers.get(getAdapterPosition()).getSource();
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    v.getContext().startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            });
        }

        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            trailerFragment.onClickItem(index);
        }
    }
}
