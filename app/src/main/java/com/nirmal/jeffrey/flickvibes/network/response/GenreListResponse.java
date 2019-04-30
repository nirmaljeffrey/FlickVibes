package com.nirmal.jeffrey.flickvibes.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nirmal.jeffrey.flickvibes.model.Genre;
import java.util.ArrayList;

public class GenreListResponse {

  @SerializedName("genres")
  @Expose
  private ArrayList<Genre> genreList;

  public ArrayList<Genre> getGenreList() {
    return genreList;
  }
}
