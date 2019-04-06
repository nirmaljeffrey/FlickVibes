package com.nirmal.jeffrey.flickvibes.network;

import com.nirmal.jeffrey.flickvibes.network.NetworkUtil.NetworkConstants;
import com.nirmal.jeffrey.flickvibes.network.response.MovieListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {


  //Popular Movie List
  @GET(NetworkConstants.POPULAR_MOVIE_PATH)
  Call<MovieListResponse> getPopularMovieList(@Query(NetworkConstants.PAGE_PARAMETER)int page);
  //Top Rated Movie List
  @GET(NetworkConstants.TOP_RATER_MOVIE_PATH)
  Call<MovieListResponse> getTopRatedMovieList(@Query(NetworkConstants.PAGE_PARAMETER)int page);
  //Upcoming Movie List
  @GET(NetworkConstants.UPCOMING_MOVIE_PATH)
  Call<MovieListResponse> getUpcomingMovieList(@Query(NetworkConstants.PAGE_PARAMETER)int page);

}
