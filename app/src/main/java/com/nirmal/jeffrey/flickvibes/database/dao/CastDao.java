package com.nirmal.jeffrey.flickvibes.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.nirmal.jeffrey.flickvibes.model.Cast;
import java.util.List;

@Dao
public interface CastDao {

  @Insert(onConflict = REPLACE)
  void insertCasts(List<Cast> casts);

  @Query("SELECT * FROM cast_table WHERE movie_id=:movieId ORDER BY cast_order ASC")
  LiveData<List<Cast>> getAllCastForMovie(int movieId);

  @Query("DELETE  FROM cast_table")
  void deleteAllCast();

}
