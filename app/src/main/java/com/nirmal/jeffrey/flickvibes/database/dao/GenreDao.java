package com.nirmal.jeffrey.flickvibes.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import com.nirmal.jeffrey.flickvibes.model.Genre;
import java.util.List;

@Dao
public abstract class GenreDao {
  @Insert(onConflict = REPLACE)
 public abstract void _insertGenres(List<Genre> genres);

  @Query("SELECT * FROM genre_table WHERE movie_id=:movieId")
 public abstract LiveData<List<Genre>> getAllGenreForMovie(int movieId);

  @Query("DELETE  FROM genre_table")
  public  abstract void deleteAllGenre();
  @Transaction
  public void insertGenres(List<Genre> genres){
    deleteAllGenre();
    _insertGenres(genres);
  }

}
