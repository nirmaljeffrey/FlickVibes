package com.nirmal.jeffrey.flickvibes.network;

import com.nirmal.jeffrey.flickvibes.util.Constants;
import com.nirmal.jeffrey.flickvibes.network.response.MovieListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {



  @GET("movie/{type}")
  Call<MovieListResponse> getMovieList(
      @Path ("type")String type,
      @Query(Constants.PAGE_PARAMETER)int page);



}
