package com.nirmal.jeffrey.flickvibes.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nirmal.jeffrey.flickvibes.model.Trailer;
import java.util.ArrayList;

public class TrailerListResponse {

  @SerializedName("results")
  @Expose
  private ArrayList<Trailer> trailerList;

  public ArrayList<Trailer> getTrailerList() {
    return trailerList;
  }
}
