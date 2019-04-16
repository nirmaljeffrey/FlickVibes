package com.nirmal.jeffrey.flickvibes.repository;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;
import com.nirmal.jeffrey.flickvibes.database.MovieDatabase;
import com.nirmal.jeffrey.flickvibes.database.dao.CastDao;
import com.nirmal.jeffrey.flickvibes.database.dao.GenreDao;
import com.nirmal.jeffrey.flickvibes.database.dao.MovieDao;
import com.nirmal.jeffrey.flickvibes.database.dao.ReviewDao;
import com.nirmal.jeffrey.flickvibes.database.dao.TrailerDao;
import com.nirmal.jeffrey.flickvibes.executor.AppExecutor;
import com.nirmal.jeffrey.flickvibes.model.Cast;
import com.nirmal.jeffrey.flickvibes.model.Genre;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.model.Review;
import com.nirmal.jeffrey.flickvibes.model.Trailer;
import com.nirmal.jeffrey.flickvibes.network.WebServiceGenerator;
import com.nirmal.jeffrey.flickvibes.network.response.ApiResponse;
import com.nirmal.jeffrey.flickvibes.network.response.CastListResponse;
import com.nirmal.jeffrey.flickvibes.network.response.GenreListResponse;
import com.nirmal.jeffrey.flickvibes.network.response.MovieListResponse;
import com.nirmal.jeffrey.flickvibes.network.response.ReviewListResponse;
import com.nirmal.jeffrey.flickvibes.network.response.TrailerListResponse;
import com.nirmal.jeffrey.flickvibes.util.DatabaseUtils;
import com.nirmal.jeffrey.flickvibes.util.NetworkBoundResource;
import com.nirmal.jeffrey.flickvibes.util.Resource;
import java.util.List;

public class MovieRepository {

  private static final String TAG = "MovieRepository";
  private static MovieRepository instance;
  private MovieDao movieDao;
  private ReviewDao reviewDao;
  private TrailerDao trailerDao;
  private CastDao castDao;
  private GenreDao genreDao;

  private MovieRepository(Context context) {
    movieDao = MovieDatabase.getInstance(context).getMovieDao();
    reviewDao = MovieDatabase.getInstance(context).getReviewDao();
    trailerDao = MovieDatabase.getInstance(context).getTrailerDao();
    castDao = MovieDatabase.getInstance(context).getCastDao();
    genreDao = MovieDatabase.getInstance(context).getGenreDao();
  }

  public static MovieRepository getInstance(Context context) {
    if (instance == null) {
      instance = new MovieRepository(context);
    }
    return instance;
  }

  public LiveData<Resource<List<Movie>>> getMovieListByTypeApi(final String type,
      final int pageNumber) {
    return new NetworkBoundResource<List<Movie>, MovieListResponse>(AppExecutor.getInstance()) {


      @Override
      protected void saveCallResult(@NonNull MovieListResponse item) {
        if (item.getMovieList() != null) {//if apiKey is expired the movieList will be null
          List<Movie> movieList = item.getMovieList();
          for (Movie movie : movieList) {
            movie.setMovieListType(type);
          }
          movieDao.insertMovies(movieList);
        }
      }

      @Override
      protected boolean shouldFetch(@Nullable List<Movie> data) {
        return true;
      }

      @NonNull
      @Override
      protected LiveData<List<Movie>> loadFromDb() {
        SimpleSQLiteQuery query = DatabaseUtils.getSQLiteQuery(type, pageNumber);

        return movieDao.getMoviesByType(query);


      }


      @NonNull
      @Override
      protected LiveData<ApiResponse<MovieListResponse>> createCall() {
        return WebServiceGenerator.getMovieApi().getMovieList(type, pageNumber);
      }
    }.getAsLiveData();

  }

  public LiveData<Resource<List<Movie>>> searchMoviesApi(String query, int pageNumber) {
    return new NetworkBoundResource<List<Movie>, MovieListResponse>(AppExecutor.getInstance()) {
      @Override
      protected void saveCallResult(@NonNull MovieListResponse item) {
        if (item.getMovieList() != null) {//if apiKey is expired the movieList will be null
          Movie[] movies = new Movie[item.getMovieList().size()];
          int index = 0;
          for (long rowId : movieDao
              .insertMovies((Movie[]) (item.getMovieList().toArray(movies)))) {
            if (rowId == -1) {
              Movie movie = movies[index];
              // if the movies already exists, update them
              movieDao.updateMovies(movie.getId(),
                  movie.getTitle(),
                  movie.getPosterPath(),
                  movie.getBackdropPath(),
                  movie.getOverview(),
                  movie.getReleaseDate(),
                  movie.getVoteAverage(),
                  movie.getPopularity());
            }
            index++;
          }

        }
      }

      @Override
      protected boolean shouldFetch(@Nullable List<Movie> data) {
        return true;
      }

      @NonNull
      @Override
      protected LiveData<List<Movie>> loadFromDb() {
        return movieDao.searchMovies(query, pageNumber);
      }

      @NonNull
      @Override
      protected LiveData<ApiResponse<MovieListResponse>> createCall() {
        return WebServiceGenerator.getMovieApi().searchMovieList(query, pageNumber);
      }
    }.getAsLiveData();
  }

  public LiveData<Resource<List<Review>>> getReviewsApi(int movieId) {
    return new NetworkBoundResource<List<Review>, ReviewListResponse>(AppExecutor.getInstance()) {

      @Override
      protected void saveCallResult(@NonNull ReviewListResponse item) {
        if (item.getReviewList() != null) {
          List<Review> reviews = item.getReviewList();
          for (Review review : reviews) {
            review.setMovieId(movieId);

          }
          reviewDao.insertReviews(reviews);

        }
      }

      @Override
      protected boolean shouldFetch(@Nullable List<Review> data) {
        return true;
      }


      @NonNull
      @Override
      protected LiveData<List<Review>> loadFromDb() {
        return reviewDao.getAllReviewsForMovie(movieId);
      }

      @NonNull
      @Override
      protected LiveData<ApiResponse<ReviewListResponse>> createCall() {
        return WebServiceGenerator.getMovieApi().getReviewList(movieId);
      }
    }.getAsLiveData();
  }

  public LiveData<Resource<List<Trailer>>> getTrailersApi(int movieId) {
    return new NetworkBoundResource<List<Trailer>, TrailerListResponse>(AppExecutor.getInstance()) {

      @Override
      protected void saveCallResult(@NonNull TrailerListResponse item) {
        if (item.getTrailerList() != null) {
          List<Trailer> trailerList = item.getTrailerList();
          for (Trailer trailer : trailerList) {
            trailer.setMovieId(movieId);
          }
          trailerDao.insertTrailers(trailerList);

        }
      }

      @Override
      protected boolean shouldFetch(@Nullable List<Trailer> data) {
        return true;
      }

      @NonNull
      @Override
      protected LiveData<List<Trailer>> loadFromDb() {
        return trailerDao.getAllTrailerForMovie(movieId);
      }

      @NonNull
      @Override
      protected LiveData<ApiResponse<TrailerListResponse>> createCall() {
        return WebServiceGenerator.getMovieApi().getTrailerList(movieId);
      }
    }.getAsLiveData();

  }

  public LiveData<Resource<List<Cast>>> getCastApi(int movieId) {
    return new NetworkBoundResource<List<Cast>, CastListResponse>(AppExecutor.getInstance()) {

      @Override
      protected void saveCallResult(@NonNull CastListResponse item) {
        if (item.getCastList() != null) {
          List<Cast> castList = item.getCastList();
          for (Cast cast : castList) {
            cast.setMovieId(movieId);
          }
          castDao.insertCasts(castList);

        }
      }

      @Override
      protected boolean shouldFetch(@Nullable List<Cast> data) {
        return true;
      }

      @NonNull
      @Override
      protected LiveData<List<Cast>> loadFromDb() {
        return castDao.getAllCastForMovie(movieId);
      }

      @NonNull
      @Override
      protected LiveData<ApiResponse<CastListResponse>> createCall() {
        return WebServiceGenerator.getMovieApi().getCastList(movieId);
      }
    }.getAsLiveData();
  }

  public LiveData<Resource<List<Genre>>> getGenreApi(int movieId) {
    return new NetworkBoundResource<List<Genre>, GenreListResponse>(AppExecutor.getInstance()) {

      @Override
      protected void saveCallResult(@NonNull GenreListResponse item) {
        if (item.getGenreList() != null) {
          List<Genre> genreList = item.getGenreList();
          for (Genre genre : genreList) {
            genre.setMovieId(movieId);
          }
          genreDao.insertGenres(genreList);
        }
      }

        @Override
        protected boolean shouldFetch (@Nullable List < Genre > data) {
          return true;
        }

        @NonNull
        @Override
        protected LiveData<List<Genre>> loadFromDb () {
          return genreDao.getAllGenreForMovie(movieId);
        }

        @NonNull
        @Override
        protected LiveData<ApiResponse<GenreListResponse>> createCall () {
          return WebServiceGenerator.getMovieApi().getGenreList(movieId);
        }

    }.getAsLiveData();

  }
}
