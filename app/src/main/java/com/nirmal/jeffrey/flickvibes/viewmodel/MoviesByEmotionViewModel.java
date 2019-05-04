package com.nirmal.jeffrey.flickvibes.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.repository.MovieRepository;
import com.nirmal.jeffrey.flickvibes.util.Resource;
import com.nirmal.jeffrey.flickvibes.util.Resource.Status;
import java.util.List;

public class MoviesByEmotionViewModel extends AndroidViewModel {

  private MovieRepository movieRepository;
  private MediatorLiveData<Resource<List<Movie>>> moviesByEmotion = new MediatorLiveData<>();

  public MoviesByEmotionViewModel(@NonNull Application application) {
    super(application);
    movieRepository = MovieRepository.getInstance(application);
  }

  public void getMovieListByEmotionApi(int genre, int pageNumber) {
    final LiveData<Resource<List<Movie>>> repositorySource = movieRepository
        .getMoviesByEmotionApi(genre, pageNumber);
    moviesByEmotion.addSource(repositorySource, listResource -> {
      if (listResource != null) {
        moviesByEmotion.setValue(listResource);
        if (listResource.status == Status.SUCCESS || listResource.status == Status.ERROR) {
          moviesByEmotion.removeSource(repositorySource);
        }
      } else {
        moviesByEmotion.removeSource(repositorySource);
      }
    });
  }

  public LiveData<Resource<List<Movie>>> getMoviesByEmotion() {
    return moviesByEmotion;
  }
  public LiveData<Movie> getMovie(int movieId){
    return movieRepository.getMovie(movieId);
  }
}
