package com.nirmal.jeffrey.flickvibes.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.nirmal.jeffrey.flickvibes.model.Trailer;
import java.util.List;
@Dao
public interface TrailerDao {
  @Insert(onConflict = REPLACE)
  void insertTrailers(List<Trailer> trailers);

  @Query("SELECT * FROM trailer_table WHERE movie_id=:movieId")
  LiveData<List<Trailer>> getAllTrailerForMovie(int movieId);

  @Query("DELETE  FROM trailer_table")
  void deleteAllTrailers();

}
