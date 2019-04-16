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
private RequestManager requestManager;
public TrailerAdapter(RequestManager requestManager){
  this.requestManager=requestManager;
}
public void setTrailerData(ArrayList<Trailer> trailerArrayList){
  this.trailerArrayList=trailerArrayList;
  notifyDataSetChanged();
}
  @NonNull
  @Override
  public TrailerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_item,parent,false);

    return new TrailerListViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull TrailerListViewHolder holder, int position) {
    Trailer trailer = trailerArrayList.get(position);
    holder.trailerTitleTextView.setText(trailer.getName());
    if(trailer.getKey()!=null){
      String trailerUrl = NetworkUtils.buildTrailerThumbnailUrl(trailer.getKey());
      requestManager.load(trailerUrl).centerCrop().into(holder.trailerImageView);
    }
  }

  @Override
  public int getItemCount() {
    return 0;
  }

  static class TrailerListViewHolder extends RecyclerView.ViewHolder{
@BindView(R.id.trailer_image_view)
    ImageView trailerImageView;
@BindView(R.id.trailer_title_text_view)
    TextView trailerTitleTextView;
  TrailerListViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this,itemView);
  }
}
}
