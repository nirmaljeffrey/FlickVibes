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


public class MovieListViewModel extends AndroidViewModel {

  private static final String TAG = "MovieListViewModel";
  private static final String QUERY_EXHAUSTED="No More Results";
  private MovieRepository movieRepository;

  private MediatorLiveData<Resource<List<Movie>>> moviesByType =new MediatorLiveData<>();
  private MediatorLiveData<Resource<List<Movie>>> moviesFromSearch=new MediatorLiveData<>();
  //Query Extras
  private boolean isQueryExhausted;
  private  boolean isPerformingQuery;
  private int pageNumber;
  private String query;
  public MovieListViewModel(@NonNull Application application) {
    super(application);
    movieRepository=MovieRepository.getInstance(application);

  }




  public LiveData<Resource<List<Movie>>>getMoviesFromSearch(){
    return moviesFromSearch;
  }
public LiveData<Resource<List<Movie>>> getMoviesByType(){
    return moviesByType;

}

public void getMovieListFromSearchApi(String query, int pageNumber){
if(!isPerformingQuery){
  if(pageNumber==0){
    pageNumber=1;
  }
  this.pageNumber=pageNumber;
this.query=query;
isQueryExhausted=false;
executeSearch();
}
}

private void executeSearch(){

    isPerformingQuery=true;
  final LiveData<Resource<List<Movie>>> moviesResults = movieRepository.searchMoviesApi(query,pageNumber);
  moviesFromSearch.addSource(moviesResults, listResource -> {
    if (listResource!=null){
      moviesFromSearch.setValue(listResource);
      if (listResource.status==Status.SUCCESS){
        isPerformingQuery=false;
        if (listResource.data!=null){
          if (listResource.data.size()==0){

            moviesFromSearch.setValue(
                new Resource<>(Status.ERROR, listResource.data, QUERY_EXHAUSTED));
          }
        }
          moviesFromSearch.removeSource(moviesResults);
      }
      else if (listResource.status==Status.ERROR){
        isPerformingQuery=false;
        moviesFromSearch.removeSource(moviesResults );

      }
    }else {
      moviesFromSearch.removeSource(moviesResults);
    }


  });

}
  public void getMovieListByTypeApi(String query, int pageNumber){
    final LiveData<Resource<List<Movie>>> repositorySource = movieRepository.getMovieListByTypeApi(query,pageNumber);
    moviesByType.addSource(repositorySource, listResource -> {
      if(listResource!=null){
        moviesByType.setValue(listResource);
        if(listResource.status== Status.SUCCESS|| listResource.status==Status.ERROR){
          moviesByType.removeSource(repositorySource);
        }

      }else {
        moviesByType.removeSource(repositorySource);
      }

    });

  }
  public LiveData<List<Movie>> getFavoriteMovies(){
    return movieRepository.getFavoriteMovies();
  }




}


