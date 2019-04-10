package com.nirmal.jeffrey.flickvibes.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nirmal.jeffrey.flickvibes.model.Cast;
import java.util.ArrayList;

public class CastListResponse {
  @SerializedName("cast")
  @Expose
  private ArrayList<Cast> castList;

  public ArrayList<Cast> getCastList() {
    return castList;
  }
}
