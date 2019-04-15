package com.nirmal.jeffrey.flickvibes.database.dao;


import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import java.util.List;

@Dao
public interface MovieDao {
  //For inserting data from searching movies
  @Insert(onConflict = IGNORE)
  long[] insertMovies(Movie... movies);

  //For updating data from searching movies
  @Query("UPDATE movie_table SET title=:title,poster_path =:posterPath,backdrop_path=:backdropPath,overview=:overView,release_date=:releaseDate,"+
      "vote_average=:voteAverage,popularity=:popularity WHERE id=:id")
  void updateMovies(int id, String title, String posterPath, String backdropPath, String overView,
      String releaseDate, Double voteAverage, Double popularity);
  // For inserting movies based on ratings, popularity, release date etc
  @Insert(onConflict = REPLACE)
  void insertMovies(List<Movie> movies);

  // For querying movies based on ratings, popularity, release date etc
  @RawQuery(observedEntities = Movie.class)
  LiveData<List<Movie>> getMoviesByType(SupportSQLiteQuery query);

  @Query("DELETE  FROM review_table")
  void deleteAllMovies();

  //For searching movies
  @Query("SELECT * FROM movie_table WHERE title LIKE '%' || :query || '%' " +
      "ORDER BY vote_average DESC LIMIT (:pageNumber * 20)")
  LiveData<List<Movie>> searchMovies(String query, int pageNumber);

  //For retrieving favourite movies
  //Boolean for true in sqlite is 1
  @Query("SELECT * FROM movie_table WHERE is_favorite = 1")
  LiveData<List<Movie>> getFavoriteMovieList();


}
