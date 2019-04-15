package com.nirmal.jeffrey.flickvibes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.RequestManager;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.adapter.SimilarMoviesAdapter.SimilarMovieViewHolder;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.util.NetworkUtils;
import java.util.ArrayList;

public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMovieViewHolder> {
  private ArrayList<Movie> movies;
  private RequestManager requestManager;
  public SimilarMoviesAdapter(RequestManager requestManager){
    this.requestManager=requestManager;
  }
  public void setSimilarMovieData(ArrayList<Movie> movieData){
    this.movies=movieData;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public SimilarMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,parent,false);
    return new SimilarMovieViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull SimilarMovieViewHolder holder, int position) {
Movie movie =movies.get(position);
if(movie.getPosterPath()!=null){
  String posterUrl = NetworkUtils.buildMovieImageURLString(NetworkUtils.POSTER_BASE_URL,movie.getPosterPath());
  requestManager.load(posterUrl).into(holder.posterImage);
}
  }

  @Override
  public int getItemCount() {
    if(movies==null) return 0;
    return movies.size();
  }

  static class SimilarMovieViewHolder extends RecyclerView.ViewHolder{
@BindView(R.id.poster_image_view)
    ImageView posterImage;

   SimilarMovieViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this,itemView);
  }
}
}
