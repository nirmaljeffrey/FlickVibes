package com.nirmal.jeffrey.flickvibes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.RequestManager;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.adapter.TrailerAdapter.TrailerListViewHolder;
import com.nirmal.jeffrey.flickvibes.model.Trailer;
import com.nirmal.jeffrey.flickvibes.util.NetworkUtils;
import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerListViewHolder> {

  private ArrayList<Trailer> trailerArrayList;
  private final RequestManager requestManager;
  private final TrailerClickListener trailerClickListener;

  public TrailerAdapter(RequestManager requestManager, TrailerClickListener trailerClickListener) {
    this.trailerClickListener = trailerClickListener;
    this.requestManager = requestManager;
  }

  public void setTrailerData(ArrayList<Trailer> trailerData) {
    //Clear the previous data before swapping
    if (this.trailerArrayList != null) {
      this.trailerArrayList.clear();
      this.trailerArrayList.addAll(trailerData);
    } else {
      this.trailerArrayList = trailerData;
    }
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public TrailerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.trailer_list_item, parent, false);

    return new TrailerListViewHolder(view, trailerClickListener, trailerArrayList);
  }

  @Override
  public void onBindViewHolder(@NonNull TrailerListViewHolder holder, int position) {
    Trailer trailer = trailerArrayList.get(position);
    holder.trailerTitleTextView.setText(trailer.getName());
    holder.trailerSite.setText(trailer.getSite());
    String trailerUrl = NetworkUtils.buildTrailerThumbnailUrl(trailer.getKey());
    requestManager.load(trailerUrl)
        .error(R.drawable.ic_poster_place_holder)
        .fallback(R.drawable.ic_poster_place_holder)
        .centerCrop()
        .into(holder.trailerImageView);
  }

  @Override
  public int getItemCount() {
    if (trailerArrayList == null) {
      return 0;
    }
    return trailerArrayList.size();
  }


  public interface TrailerClickListener {

    void onTrailerVideoClick(Trailer trailer);

    void onTrailerShareClick(Trailer trailer);
  }

  static class TrailerListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.trailer_image_view)
    ImageView trailerImageView;
    @BindView(R.id.trailer_title_text_view)
    TextView trailerTitleTextView;
    @BindView(R.id.trailer_site_label)
    TextView trailerSite;
    @BindView(R.id.trailer_share_button)
    ImageView trailerShareButton;

    TrailerListViewHolder(@NonNull View itemView, TrailerClickListener trailerClickListener,
        ArrayList<Trailer> trailerArrayList) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(view -> {
        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {
          if (trailerArrayList != null && trailerArrayList.size() > 0) {
            trailerClickListener.onTrailerVideoClick(trailerArrayList.get(position));
          }
        }
      });
      trailerShareButton.setOnClickListener(view -> {
        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {
          if (trailerArrayList != null && trailerArrayList.size() > 0) {
            trailerClickListener.onTrailerShareClick(trailerArrayList.get(position));
          }
        }
      });
    }

  }
}
