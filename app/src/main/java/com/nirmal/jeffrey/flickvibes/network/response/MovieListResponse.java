package com.nirmal.jeffrey.flickvibes.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import java.util.List;

public class MovieListResponse {

  @SerializedName("page")
  @Expose
  private int page;
  @SerializedName("results")
  @Expose
  private List<Movie> movieList;

  public List<Movie> getMovieList() {
    return movieList;
  }

  public int getPage() {
    return page;
  }
}
