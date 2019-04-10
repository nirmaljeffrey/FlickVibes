package com.nirmal.jeffrey.flickvibes.database.dao;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.nirmal.jeffrey.flickvibes.model.Review;
import java.util.List;

@Dao
public interface ReviewDao {


  @Insert(onConflict = REPLACE)
  void insertReviews(List<Review> reviews);

  @Query("SELECT * FROM review_table WHERE movie_id=:moveieId")
  List<Review> getAllReviewsForMovie(int moveieId);

  @Query("DELETE  FROM review_table")
  void deleteAllReviews();
}
