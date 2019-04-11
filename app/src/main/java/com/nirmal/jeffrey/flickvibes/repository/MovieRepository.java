package com.nirmal.jeffrey.flickvibes.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.nirmal.jeffrey.flickvibes.database.MovieDatabase;
import com.nirmal.jeffrey.flickvibes.database.dao.MovieDao;
import com.nirmal.jeffrey.flickvibes.database.dao.ReviewDao;
import com.nirmal.jeffrey.flickvibes.executor.AppExecutor;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.network.WebServiceGenerator;
import com.nirmal.jeffrey.flickvibes.network.response.ApiResponse;
import com.nirmal.jeffrey.flickvibes.network.response.MovieListResponse;
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
        return movieDao.getAllMovies(type,pageNumber);
      }

      @NonNull
      @Override
      protected LiveData<ApiResponse<MovieListResponse>> createCall() {
        return WebServiceGenerator.getMovieApi().getMovieList(type,pageNumber);
      }
    }.getAsLiveData();

}

}
