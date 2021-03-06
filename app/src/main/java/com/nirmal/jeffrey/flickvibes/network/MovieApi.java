package com.nirmal.jeffrey.flickvibes.network;


import androidx.lifecycle.LiveData;
import com.nirmal.jeffrey.flickvibes.network.apiResponse.ApiResponse;
import com.nirmal.jeffrey.flickvibes.network.response.CastListResponse;
import com.nirmal.jeffrey.flickvibes.network.response.GenreListResponse;
import com.nirmal.jeffrey.flickvibes.network.response.MovieListResponse;
import com.nirmal.jeffrey.flickvibes.network.response.ReviewListResponse;
import com.nirmal.jeffrey.flickvibes.network.response.TrailerListResponse;
import com.nirmal.jeffrey.flickvibes.util.NetworkUtils;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

  //Every call request will be appended with apiKey using interceptors.
//Movies by popularity, ratings, upcoming etc
  @GET("movie/{type}")
  LiveData<ApiResponse<MovieListResponse>> getMovieList(
      @Path("type") String type,
      @Query(NetworkUtils.PAGE_PARAMETER) int page);

  //Search Movies
  @GET("search/movie")
  LiveData<ApiResponse<MovieListResponse>> searchMovieList
  (@Query(NetworkUtils.MOVIE_QUERY_PARAMETER) String query,
      @Query(NetworkUtils.PAGE_PARAMETER) int page);

  //Trailer
  @GET("movie/{movie_id}/videos")
  LiveData<ApiResponse<TrailerListResponse>> getTrailerList(@Path("movie_id") int id);

  //Cast
  @GET("movie/{movie_id}/credits")
  LiveData<ApiResponse<CastListResponse>> getCastList(@Path("movie_id") int id);

  //Reviews
  @GET("movie/{movie_id}/reviews")
  LiveData<ApiResponse<ReviewListResponse>> getReviewList(@Path("movie_id") int id);

  //Genre
  @GET("movie/{movie_id}")
  LiveData<ApiResponse<GenreListResponse>> getGenreList(@Path("movie_id") int id);

  //Discover movies based on user's emotion
  @GET("discover/movie?sort_by=popularity.desc")
  LiveData<ApiResponse<MovieListResponse>> getMoviesListByEmotion(
      @Query(NetworkUtils.GENRE_PARAMETER) int genre,
      @Query(NetworkUtils.PAGE_PARAMETER) int page);


}
