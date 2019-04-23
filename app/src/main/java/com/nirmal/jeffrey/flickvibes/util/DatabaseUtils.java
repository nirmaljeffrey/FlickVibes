package com.nirmal.jeffrey.flickvibes.util;

import androidx.sqlite.db.SimpleSQLiteQuery;
import com.nirmal.jeffrey.flickvibes.model.Movie;

public final class DatabaseUtils {

  private DatabaseUtils() {
  }

  public static SimpleSQLiteQuery getMovieListQuery(String type, int pageNumber) {

    switch (type) {
      case NetworkUtils.POPULAR_MOVIE_PATH:
        return new SimpleSQLiteQuery(
            "SELECT * FROM movie_table WHERE is_popular = 1 ORDER BY popularity DESC LIMIT (? *20)",
            new Object[]{pageNumber});

      case NetworkUtils.TOP_RATER_MOVIE_PATH:
        return new SimpleSQLiteQuery(
            "SELECT * FROM movie_table WHERE is_top_rated = 1 ORDER BY vote_average DESC LIMIT (? *20)",
            new Object[]{pageNumber});
      case NetworkUtils.NOW_PLAYING_MOVIE_PATH:
        return new SimpleSQLiteQuery(
            "SELECT * FROM movie_table WHERE is_now_playing = 1  LIMIT (? *20)",
            new Object[]{pageNumber});
      case NetworkUtils.UPCOMING_MOVIE_PATH:
        return new SimpleSQLiteQuery(
            "SELECT * FROM movie_table WHERE is_upcoming = 1  LIMIT (? *20)",
            new Object[]{pageNumber});
      default:
        throw new IllegalArgumentException(
            "The movieList should of four types - popular, topRated, upcoming, nowPlaying");


    }


  }



  public static void setMovieTypeInPOJO(String type, Movie movie) {
    switch (type) {
      case NetworkUtils.POPULAR_MOVIE_PATH:
        movie.setPopular(true);
        break;
      case NetworkUtils.TOP_RATER_MOVIE_PATH:
        movie.setTopRated(true);
        break;
      case NetworkUtils.UPCOMING_MOVIE_PATH:
        movie.setUpcoming(true);
        break;
      case NetworkUtils.NOW_PLAYING_MOVIE_PATH:
        movie.setNowPlaying(true);
        break;
      default:
        throw new IllegalArgumentException(
            "The movieList should of four types - popular, topRated, upcoming, nowPlaying");
    }

  }
}
