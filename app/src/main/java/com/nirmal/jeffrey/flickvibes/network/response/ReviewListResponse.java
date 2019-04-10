package com.nirmal.jeffrey.flickvibes.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nirmal.jeffrey.flickvibes.model.Review;
import java.util.ArrayList;

public class ReviewListResponse {
  @SerializedName("results")
  @Expose
  private ArrayList<Review> reviewList;

  public ArrayList<Review> getReviewList() {
    return reviewList;
  }
}
