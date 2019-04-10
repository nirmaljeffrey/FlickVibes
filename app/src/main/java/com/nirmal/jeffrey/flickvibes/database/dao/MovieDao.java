package com.nirmal.jeffrey.flickvibes.database.dao;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import java.util.List;

@Dao
public interface MovieDao {

  @Insert(onConflict = REPLACE)
  void insertMovies(List<Movie> movies);

  @Query("SELECT * FROM movie_table")
  List<Movie> getAllMovies();

  @Query("DELETE  FROM review_table")
  void deleteAllMovies();

}
