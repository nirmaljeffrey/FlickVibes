package com.nirmal.jeffrey.flickvibes.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import com.nirmal.jeffrey.flickvibes.repository.MovieRepository;


public class MovieListViewModel extends AndroidViewModel {
  private MovieRepository movieRepository;


  public MovieListViewModel(@NonNull Application application) {
    super(application);
    movieRepository=MovieRepository.getInstance(application);
  }

}
