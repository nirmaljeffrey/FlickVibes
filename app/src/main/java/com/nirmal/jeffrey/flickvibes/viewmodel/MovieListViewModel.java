package com.nirmal.jeffrey.flickvibes.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.repository.MovieRepository;
import java.util.List;


public class MovieListViewModel extends AndroidViewModel {
  private MovieRepository movieRepository;


  public MovieListViewModel(@NonNull Application application) {
    super(application);
    movieRepository=MovieRepository.getInstance(application);
  }
}
