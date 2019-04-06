package com.nirmal.jeffrey.flickvibes.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.repository.MovieRepository;
import java.util.List;


public class MovieListViewModel extends ViewModel {
  private MovieRepository movieRepository;
  public MovieListViewModel(){
    movieRepository= MovieRepository.getInstance();
  }
public LiveData<List<Movie>> getMoviesList(){
    return movieRepository.getMoviesList();
}
  public void getMovieListApi(String pathType, int pageNumber){
    movieRepository.getMovieListApi(pathType,pageNumber);
  }
}
