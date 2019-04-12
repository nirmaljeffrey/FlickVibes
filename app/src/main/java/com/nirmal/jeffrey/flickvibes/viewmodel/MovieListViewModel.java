package com.nirmal.jeffrey.flickvibes.viewmodel;


import android.app.Application;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.repository.MovieRepository;
import com.nirmal.jeffrey.flickvibes.util.Resource;
import java.util.List;


public class MovieListViewModel extends AndroidViewModel {

  private static final String TAG = "MovieListViewModel";
  private MovieRepository movieRepository;
  private MediatorLiveData<Resource<List<Movie>>> movies =new MediatorLiveData<>();
  public MovieListViewModel(@NonNull Application application) {
    super(application);
    movieRepository=MovieRepository.getInstance(application);

  }
public LiveData<Resource<List<Movie>>> getMovies(){
    return movies;

}
  public void getMovieListByTypeApi(String type, int pageNumber){
    final LiveData<Resource<List<Movie>>> repositorySource = movieRepository.getMovieListByTypeApi(type,pageNumber);
    movies.addSource(repositorySource, new Observer<Resource<List<Movie>>>() {
      @Override
      public void onChanged(@Nullable Resource<List<Movie>> listResource) {
          movies.setValue(listResource);
      }
    });

  }

}