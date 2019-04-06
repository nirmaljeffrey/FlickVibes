package com.nirmal.jeffrey.flickvibes.network.NetworkUtil;

import com.nirmal.jeffrey.flickvibes.BuildConfig;

public class NetworkConstants {
  //Base Url
  public static final String MOVIE_BASE_URL="https://api.themoviedb.org/3/movie/";
  // Query Key Value
  public static final String API_KEY_VALUE= BuildConfig.ApiKey;
  //Query key Parameters
  public static final String API_KEY_PARAMETER ="api_key";
  public static final String PAGE_PARAMETER="page";
  //Url Path
  public static final String TOP_RATER_MOVIE_PATH="top_rated";
  public static final String POPULAR_MOVIE_PATH ="popular";
  public static final String UPCOMING_MOVIE_PATH="upcoming";
  public static final String NOW_PLAYING_MOVIE_PATH="now_playing";
}
