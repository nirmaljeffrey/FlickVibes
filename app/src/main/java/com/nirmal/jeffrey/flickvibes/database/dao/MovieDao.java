package com.nirmal.jeffrey.flickvibes.database.dao;


import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import java.util.List;

@Dao
public  interface MovieDao {



  //For inserting data from searching movies
  @Insert(onConflict = IGNORE)
 long[] insertMovies(Movie... movies);

  //For updating data from searching movies
  @Query("UPDATE movie_table SET title=:title,poster_path =:posterPath,backdrop_path=:backdropPath,overview=:overView,release_date=:releaseDate,"+
      "vote_average=:voteAverage,popularity=:popularity WHERE id=:id")
  void updateMovies(int id, String title, String posterPath, String backdropPath, String overView,
      String releaseDate, Double voteAverage, Double popularity);
  //For updating movie Type
 @Query("UPDATE movie_table SET is_popular = 1 WHERE id = :movieId")
 void updatePopularMovie(int movieId);
 @Query("UPDATE movie_table SET is_top_rated = 1 WHERE id = :movieId")
 void updateTopRatedMovie(int movieId);
 @Query("UPDATE movie_table SET is_upcoming = 1 WHERE id = :movieId")
 void updateUpcomingMovie(int movieId);
 @Query("UPDATE movie_table SET is_now_playing = 1 WHERE id = :movieId")
 void updateNowPlayingMovie(int movieId);


  // For querying movies based on ratings, popularity, release date etc
  @RawQuery(observedEntities = Movie.class)
 LiveData<List<Movie>> getMoviesByType(SupportSQLiteQuery query);

  @Query("DELETE  FROM review_table")
  void deleteAllMovies();
 //Get favorite movie list for app widget
 @Query("SELECT * FROM movie_table WHERE id=:movieId")
 LiveData<Movie> getMovie(int movieId);
  //For searching movies
  @Query("SELECT * FROM movie_table WHERE title LIKE '%' || :query || '%' " +
      "ORDER BY vote_average DESC LIMIT (:pageNumber * 20)")
 LiveData<List<Movie>> searchMovies(String query, int pageNumber);

  //For retrieving favourite movies
  //Boolean for true in sqlite is 1
  @Query("SELECT * FROM movie_table WHERE is_favorite = 1")
 LiveData<List<Movie>> getFavoriteMovieList();

 //Get favorite movie list for app widget
 @Query("SELECT * FROM movie_table WHERE is_favorite = 1")
 List<Movie> getFavoriteMoviesForWidget();

  //Marking a movie as favorite
  @Query("UPDATE movie_table SET is_favorite = 1 WHERE id=:movieId")
  void setMovieAsFavorite(int movieId);
  //Removing movie from favorite
  @Query("UPDATE movie_table SET is_favorite = 0 WHERE id=:movieId")
  void removeMovieFromFavorite(int movieId);

@Query("SELECT * FROM movie_table WHERE id=:movieId AND is_favorite = 1")
LiveData<Movie> getFavoriteMovie(int movieId);

//Update the genre variable in movie_table
 @Query("UPDATE movie_table SET genre=:genre WHERE id=:id ")
 void updateMovieGenre(int genre, int id);
 //Get movies list by emotion
 @Query("SELECT * FROM movie_table WHERE genre=:genre")
 LiveData<List<Movie>> getMovieListByEmotion(int genre);






}


