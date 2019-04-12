package com.nirmal.jeffrey.flickvibes.util;

import android.net.Uri;
import com.nirmal.jeffrey.flickvibes.BuildConfig;

public final class NetworkUtils {
  private NetworkUtils(){

  }

                    /** NetworkUtils used in web service **/


  //Base Url
  public static final String MOVIE_BASE_URL="https://api.themoviedb.org/3/";
  public static String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w342/";
  public static String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w500/";
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
  public static final String NOW_PLAYING_MOVIE_PATH="now_playing";



  /**
   * @return the url link in string format to access the thumbnail for the movies from movies db web server.
   */
  public static String buildMovieImageURLString(String imagePath) {
    Uri builtUri = Uri.parse(NetworkUtils.POSTER_BASE_URL).buildUpon()
        .appendEncodedPath(imagePath)
        .build();
    return builtUri.toString();
  }


}
