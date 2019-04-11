package com.nirmal.jeffrey.flickvibes.database.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import java.util.List;

@Dao
public interface MovieDao {

  @Insert(onConflict = IGNORE)
  long[] insertMovies(Movie... movies);

  @Insert(onConflict = REPLACE)
  void insertMovies(List<Movie> movies);

  @Query("SELECT * FROM movie_table WHERE movie_list_type =:type LIMIT (:PageNumber *20)")
  LiveData<List<Movie>> getAllMovies(String type, int PageNumber);

  @Query("DELETE  FROM review_table")
  void deleteAllMovies();

  @Query("SELECT * FROM movie_table WHERE title LIKE '%' || :query || '%' " +
      "ORDER BY vote_average DESC LIMIT (:pageNumber * 20)")
  LiveData<List<Movie>> searchMovies(String query, int pageNumber);


}
