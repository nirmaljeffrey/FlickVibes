package com.nirmal.jeffrey.flickvibes.util;

import android.net.Uri;
import com.nirmal.jeffrey.flickvibes.BuildConfig;

public final class NetworkUtils {
  private NetworkUtils(){

  }

                    /** NetworkUtils used in Movie web service **/


  //Base Url
  public static final String MOVIE_BASE_URL="https://api.themoviedb.org/3/";
  public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w342/";
  public static final String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w500/";
  // Query Key Value
  public static final String API_KEY_VALUE= BuildConfig.ApiKey;
  //Query key Parameters
  public static final String API_KEY_PARAMETER ="api_key";
  public static final String PAGE_PARAMETER="page";
  public static final String MOVIE_QUERY_PARAMETER="query";
  public static final String GENRE_PARAMETER="with_genres";

  //Url Path
  public static final String TOP_RATER_MOVIE_PATH="top_rated";
  public static final String POPULAR_MOVIE_PATH ="popular";
  public static final String UPCOMING_MOVIE_PATH="upcoming";
  public static final String NOW_PLAYING_MOVIE_PATH="now_playing";

  // Base url used for sharing trailer video links online
  private static final String MOVIE_YOUTUBE_WEB_TRAILER_URL = "https://www.youtube.com/watch?v=";
  private static final String MOVIE_YOUTUBE_APP_TRAILER_URL="vnd.youtube:";
  private static final String MOVIE_TRAILER_THUMBNAIL_URL_PART_ONE = "https://img.youtube.com/vi/";
  private static final String MOVIE_TRAILER_THUMBNAIL_URL_PART_TWO = "/0.jpg";
  public static final String TRAILER_MIME_TYPE = "text/plain";

// Base Url for Cast
 private static final String MOVIE_CAST_WEB_URL= "https://www.google.com/search?q=";
 private static final String MOVIE_CAST_WEB_PARAMETER=" movies";




  /**
   * @return the url link in string format to access the thumbnail for the movies from movies db web server.
   */
  public static String buildMovieImageURLString(String url,String imagePath) {
    Uri builtUri = Uri.parse(url).buildUpon()
        .appendEncodedPath(imagePath)
        .build();
    return builtUri.toString();
  }
  /**
   * @return the url link in string format to access the thumbnail for the movies from movies db web server.
   */
  public static String buildTrailerThumbnailUrl(String key){
    return MOVIE_TRAILER_THUMBNAIL_URL_PART_ONE + key+ MOVIE_TRAILER_THUMBNAIL_URL_PART_TWO;
  }
  /**
   * @return the url link to access the trailer video for the movies from youtube.com.
   */
  public static Uri buildYoutubeAppVideoUrl(String key){
    return Uri.parse(MOVIE_YOUTUBE_APP_TRAILER_URL+key);
  }

  /**
   * @return the url link to access the trailer video for the movies from youtube app.
   */
  public static Uri buildYoutubeWebVideoUrl(String key){
    return Uri.parse(MOVIE_YOUTUBE_WEB_TRAILER_URL+key);
  }

  /**
   * @return the url link to access the Cast for the movies from google.com.
   */
  public static Uri buildCastWebUrl(String castName){
    return Uri.parse(MOVIE_CAST_WEB_URL+castName+MOVIE_CAST_WEB_PARAMETER);
  }



}
