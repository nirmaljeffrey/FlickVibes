package com.nirmal.jeffrey.flickvibes.util;

import androidx.sqlite.db.SimpleSQLiteQuery;

public final class DatabaseUtils {

  private DatabaseUtils() {
  }

  public static SimpleSQLiteQuery getSQLiteQuery(String type, int pageNumber) {

    switch (type) {
      case NetworkUtils.POPULAR_MOVIE_PATH:
        return new SimpleSQLiteQuery(
            "SELECT * FROM movie_table WHERE movie_list_type = ? ORDER BY ? DESC LIMIT (? *20)",
            new Object[]{type, "popularity", pageNumber});
      case NetworkUtils.TOP_RATER_MOVIE_PATH:
        return new SimpleSQLiteQuery(
            "SELECT * FROM movie_table WHERE movie_list_type = ? ORDER BY ? DESC LIMIT (? *20)",
            new Object[]{type, "vote_average", pageNumber});
      default:
        return new SimpleSQLiteQuery(
            "SELECT * FROM movie_table WHERE movie_list_type = ?  LIMIT (? *20)",
            new Object[]{type, pageNumber});


    }


  }

}
