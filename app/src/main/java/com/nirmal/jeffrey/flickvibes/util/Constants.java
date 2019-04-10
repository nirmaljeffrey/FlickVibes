package com.nirmal.jeffrey.flickvibes.util;

import com.nirmal.jeffrey.flickvibes.BuildConfig;

public class Constants {

                    /** Constants used in web service **/


  //Base Url
  public static final String MOVIE_BASE_URL="https://api.themoviedb.org/3/";
  // Query Key Value
  public static final String API_KEY_VALUE= BuildConfig.ApiKey;
  //Query key Parameters
  public static final String API_KEY_PARAMETER ="api_key";
  public static final String PAGE_PARAMETER="page";
  public static final String MOVIE_QUERY_PARAMETER="query";

  //Url Path
  public static final String TOP_RATER_MOVIE_PATH="top_rated";
  public static final String POPULAR_MOVIE_PATH ="popular";
  public static final String UPCOMING_MOVIE_PATH="upcoming";

}
