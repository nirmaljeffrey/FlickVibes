package com.nirmal.jeffrey.flickvibes.repository;

import androidx.lifecycle.LiveData;
import com.nirmal.jeffrey.flickvibes.model.Movie;

import com.nirmal.jeffrey.flickvibes.network.MovieApiClient;
import java.util.List;

public class MovieRepository {
  private static MovieRepository instance;
  private MovieApiClient movieApiClient;
  private MovieRepository(){
    movieApiClient= MovieApiClient.getInstance();

  }
  public static MovieRepository getInstance(){
    if(instance==null){
      instance=new MovieRepository();
    }
    return instance;
  }
  public LiveData<List<Movie>> getMoviesList(){
    return movieApiClient.getMoviesList();
  }
  public void getMovieListApi(String pathType, int pageNumber){
    if(pageNumber==0){
      pageNumber=1;
    }
    movieApiClient.getMovieListApi(pathType,pageNumber);
  }


}
