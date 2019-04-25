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
import com.nirmal.jeffrey.flickvibes.adapter.MovieAdapter.MovieListViewHolder;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.util.NetworkUtils;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieListViewHolder> {

  private OnMovieItemClickLister onMovieItemClickLister;
private ArrayList<Movie> movieArrayList;
private RequestManager requestManager;

public MovieAdapter(RequestManager requestManager,OnMovieItemClickLister onMovieItemClickLister){
this.onMovieItemClickLister=onMovieItemClickLister;
this.requestManager=requestManager;
}

public void setMovieData(ArrayList<Movie> movieData){
  //Clear the previous data before swapping
  if(movieArrayList!=null) {
    this.movieArrayList.clear();
    this.movieArrayList.addAll(movieData);
  }else {
    this.movieArrayList =movieData;
  }
  notifyDataSetChanged();
}
  @NonNull
  @Override
  public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,parent,false);

    return new MovieListViewHolder(view,onMovieItemClickLister,movieArrayList);
  }

  @Override
  public void onBindViewHolder(@NonNull MovieListViewHolder holder, int position) {
       Movie movie= movieArrayList.get(position);
       if (movie.isFavorite()){
         holder.favoriteTag.setVisibility(View.VISIBLE);

       }else {
         holder.favoriteTag.setVisibility(View.GONE);
       }
       if(movie.getPosterPath()!=null){
         // Method to create complete url string of the poster
         String posterUrl = NetworkUtils.buildMovieImageURLString(NetworkUtils.POSTER_BASE_URL,movie.getPosterPath());
              requestManager
                  .load(posterUrl)
                  .into(holder.posterImageView);

       }


  }

  @Override
  public int getItemCount() {
    if(movieArrayList==null)return 0;
    return movieArrayList.size();
  }

  static class MovieListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.poster_image_view)
    ImageView posterImageView;
    @BindView(R.id.movie_favorite_tag)
        ImageView favoriteTag;

    MovieListViewHolder(@NonNull View itemView, OnMovieItemClickLister itemClickLister,
        ArrayList<Movie> movies) {
      super(itemView);
      ButterKnife.bind(this, itemView);
        posterImageView.setOnClickListener(view -> {
          if(itemClickLister!=null) {
            int adapterPosition = getAdapterPosition();
            if(adapterPosition!=RecyclerView.NO_POSITION){
              if(movies!=null && movies.size()>0){
                itemClickLister.onClickItem(movies.get(adapterPosition));
              }

            }
          }

        });

    }
  }

  public interface OnMovieItemClickLister {
    void onClickItem(Movie movie);
  }
}
