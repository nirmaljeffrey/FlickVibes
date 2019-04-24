package com.nirmal.jeffrey.flickvibes.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.nirmal.jeffrey.flickvibes.model.Genre;
import java.util.List;

@Dao
public  interface GenreDao {
  @Insert(onConflict = REPLACE)
  void insertGenres(List<Genre> genres);

  @Query("SELECT * FROM genre_table WHERE movie_id=:movieId")
  LiveData<List<Genre>> getAllGenreForMovie(int movieId);

  @Query("DELETE  FROM genre_table")
 void deleteAllGenre();



}
