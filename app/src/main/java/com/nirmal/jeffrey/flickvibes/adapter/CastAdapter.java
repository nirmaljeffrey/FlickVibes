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
import com.nirmal.jeffrey.flickvibes.adapter.CastAdapter.CastListViewHolder;
import com.nirmal.jeffrey.flickvibes.model.Cast;
import com.nirmal.jeffrey.flickvibes.util.NetworkUtils;
import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastListViewHolder> {
private ArrayList<Cast> castArrayList;
private RequestManager requestManager;
public CastAdapter(RequestManager requestManager){
  this.requestManager=requestManager;
}
public void setCastData(ArrayList<Cast> castArrayList){
  this.castArrayList=castArrayList;
  notifyDataSetChanged();
}
  @NonNull
  @Override
  public CastListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_list_item,parent,false);
  return new CastListViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull CastListViewHolder holder, int position) {
    Cast cast= castArrayList.get(position);
    holder.castName.setText(cast.getCastName());
    if(cast.getCastProfilePath()!=null) {
      String castUrl = NetworkUtils
          .buildMovieImageURLString(NetworkUtils.POSTER_BASE_URL, cast.getCastProfilePath());
      requestManager.load(castUrl).centerCrop().into(holder.castImage);
    }
  }

  @Override
  public int getItemCount() {
  if(castArrayList==null) return 0;
    return castArrayList.size();
  }

  static class CastListViewHolder extends RecyclerView.ViewHolder{
@BindView(R.id.cast_image_View)
    ImageView castImage;
@BindView(R.id.cast_text_view)
    TextView castName;
  CastListViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this,itemView);
  }
}
}
