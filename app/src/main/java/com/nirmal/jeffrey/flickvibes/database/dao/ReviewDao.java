package com.nirmal.jeffrey.flickvibes.database.dao;


import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.nirmal.jeffrey.flickvibes.model.Review;
import java.util.List;

@Dao
public interface ReviewDao {


  @Insert(onConflict = REPLACE)
  void insertReviews(List<Review> reviews);

  @Query("SELECT * FROM review_table WHERE movie_id=:movieId")
  LiveData<List<Review>> getAllReviewsForMovie(int movieId);

  @Query("DELETE  FROM review_table")
  void deleteAllReviews();
}
