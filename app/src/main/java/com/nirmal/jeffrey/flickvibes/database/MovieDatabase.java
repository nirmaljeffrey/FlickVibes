package com.nirmal.jeffrey.flickvibes.database;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.nirmal.jeffrey.flickvibes.database.dao.CastDao;
import com.nirmal.jeffrey.flickvibes.database.dao.GenreDao;
import com.nirmal.jeffrey.flickvibes.database.dao.MovieDao;
import com.nirmal.jeffrey.flickvibes.database.dao.ReviewDao;
import com.nirmal.jeffrey.flickvibes.database.dao.TrailerDao;
import com.nirmal.jeffrey.flickvibes.model.Cast;
import com.nirmal.jeffrey.flickvibes.model.Genre;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.model.Review;
import com.nirmal.jeffrey.flickvibes.model.Trailer;

@Database(entities = {Movie.class, Review.class, Trailer.class, Cast.class,
    Genre.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

  private static final String DATABASE_NAME = "movies_db";

  private static MovieDatabase instance;

  public static synchronized MovieDatabase getInstance(Context context) {
    if (instance == null) {
      instance = Room
          .databaseBuilder(context.getApplicationContext(), MovieDatabase.class, DATABASE_NAME)
          .build();
    }
    return instance;
  }

  public abstract MovieDao getMovieDao();

  public abstract ReviewDao getReviewDao();

  public abstract TrailerDao getTrailerDao();

  public abstract GenreDao getGenreDao();

  public abstract CastDao getCastDao();

}
