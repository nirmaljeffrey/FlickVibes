package com.nirmal.jeffrey.flickvibes.network;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.nirmal.jeffrey.flickvibes.executor.AppExecutor;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.util.Constants;
import com.nirmal.jeffrey.flickvibes.network.response.MovieListResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

  private static MovieApiClient instance;
  private MutableLiveData<List<Movie>> movieList;
  private RetrieveMoviesRunnable retrieveMoviesRunnable;

  private MovieApiClient() {
    movieList = new MutableLiveData<>();
  }

  public static MovieApiClient getInstance() {
    if (instance == null) {
      instance = new MovieApiClient();
    }
    return instance;
  }

  public LiveData<List<Movie>> getMoviesList() {
    return movieList;
  }

  /**
   * Method to make a network request to get movie list
   */
  public void getMovieListApi(String pathType,int pageNumber) {
    if(retrieveMoviesRunnable!=null){
      retrieveMoviesRunnable=null;
    }
    retrieveMoviesRunnable= new RetrieveMoviesRunnable(pathType,pageNumber);
    final Future handler = AppExecutor.getInstance().getNetworkIO().submit(retrieveMoviesRunnable);

    AppExecutor.getInstance().getNetworkIO().schedule(new Runnable() {
      @Override
      public void run() {
        handler.cancel(true);
      }
    }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
  }

  private class RetrieveMoviesRunnable implements Runnable {

    private String pathType;
    private int pageNumber;
    private boolean requestCancelled;

    public RetrieveMoviesRunnable(String pathType, int pageNumber) {
      this.pathType = pathType;
      this.pageNumber = pageNumber;
      this.requestCancelled = false;
    }

    /**
     * @param pathType popular movies or top-rated or upcoming or now playing movies
     * @param pageNumber page number for the movie list to be requested
     * @return Call object with input parameters pathType and PageNumber
     */
    private Call<MovieListResponse> getMovies(String pathType, int pageNumber) {
      return WebServiceGenerator.getMovieApi().getMovieList(pathType, pageNumber);
    }

    //Method to cancel the retrofit network request
    private void cancelRequest() {
      requestCancelled = true;
    }

    @Override
    public void run() {
      try {
        Call<MovieListResponse> movieListResponseCall = getMovies(pathType, pageNumber);

        if (requestCancelled) {
          movieListResponseCall.cancel();
        }
        Response response = movieListResponseCall.execute();

        if (response.code()==200) {
          MovieListResponse movieListResponse = (MovieListResponse) response.body();
          List<Movie> list = new ArrayList<>(movieListResponse.getMovieList());
          if (pageNumber == 1) {
            movieList.postValue(list);
          }else {
            // If page number from the query is greater than 1, append the movie list obtained to the movie list of page 1
            List<Movie> currentMovies = movieList.getValue();
            currentMovies.addAll(list);
            movieList.postValue(currentMovies);
          }
        } else {
            movieList.postValue(null);
        }

      } catch (IOException e) {
        e.printStackTrace();
        movieList.postValue(null);
      }

    }


  }

}
