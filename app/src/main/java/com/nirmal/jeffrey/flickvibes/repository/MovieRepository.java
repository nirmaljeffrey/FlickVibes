package com.nirmal.jeffrey.flickvibes.repository;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;
import com.nirmal.jeffrey.flickvibes.database.MovieDatabase;
import com.nirmal.jeffrey.flickvibes.database.dao.MovieDao;
import com.nirmal.jeffrey.flickvibes.database.dao.ReviewDao;
import com.nirmal.jeffrey.flickvibes.executor.AppExecutor;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.network.WebServiceGenerator;
import com.nirmal.jeffrey.flickvibes.network.response.ApiResponse;
import com.nirmal.jeffrey.flickvibes.network.response.MovieListResponse;
import com.nirmal.jeffrey.flickvibes.util.DatabaseUtils;
import com.nirmal.jeffrey.flickvibes.util.NetworkBoundResource;
import com.nirmal.jeffrey.flickvibes.util.Resource;
import java.util.List;

public class MovieRepository {
  private static MovieRepository instance;
  private MovieDao movieDao;
  private ReviewDao reviewDao;

  private MovieRepository(Context context){
       movieDao= MovieDatabase.getInstance(context).getMovieDao();
      // reviewDao=MovieDatabase.getInstance(context).getReviewDao();

  }
  public static MovieRepository getInstance(Context context){
    if(instance==null){
      instance=new MovieRepository(context);
    }
    return instance;
  }

public LiveData<Resource<List<Movie>>> getMovieListByTypeApi(final String type, final int pageNumber){
    return new NetworkBoundResource<List<Movie>, MovieListResponse>(AppExecutor.getInstance()){



      @Override
      protected void saveCallResult(@NonNull MovieListResponse item) {
       if(item.getMovieList()!=null){//if apiKey is expired the movieList will be null
         List<Movie> movieList =item.getMovieList();
         for(Movie movie:movieList){
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
        SimpleSQLiteQuery query =DatabaseUtils.getSQLiteQuery(type,pageNumber);

          return movieDao.getMoviesByType(query);


      }


      @NonNull
      @Override
      protected LiveData<ApiResponse<MovieListResponse>> createCall() {
        return WebServiceGenerator.getMovieApi().getMovieList(type,pageNumber);
      }
    }.getAsLiveData();

}
public LiveData<Resource<List<Movie>>> searchMoviesApi(String query,int pageNumber){
    return new NetworkBoundResource<List<Movie>,MovieListResponse>(AppExecutor.getInstance()){
      @Override
      protected void saveCallResult(@NonNull MovieListResponse item) {
        if(item.getMovieList()!=null){//if apiKey is expired the movieList will be null
          Movie[] movies =new Movie[item.getMovieList().size()];
          int index =0;
          for(long rowId:movieDao.insertMovies((Movie[])(item.getMovieList().toArray(movies)))){
             if(rowId == -1){
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
        return movieDao.searchMovies(query,pageNumber);
      }

      @NonNull
      @Override
      protected LiveData<ApiResponse<MovieListResponse>> createCall() {
        return WebServiceGenerator.getMovieApi().searchMovieList(query,pageNumber);
      }
    }.getAsLiveData();
}

}
