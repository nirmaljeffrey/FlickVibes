package com.nirmal.jeffrey.flickvibes.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.nirmal.jeffrey.flickvibes.model.Review;
import com.nirmal.jeffrey.flickvibes.model.Trailer;
import com.nirmal.jeffrey.flickvibes.repository.MovieRepository;
import com.nirmal.jeffrey.flickvibes.util.Resource;
import java.util.List;

public class MovieDetailViewModel extends AndroidViewModel {
  private MovieRepository movieRepository;

  public MovieDetailViewModel(@NonNull Application application) {
    super(application);
    movieRepository = MovieRepository.getInstance(application);
  }

  public LiveData<Resource<List<Review>>> getReviewsApi(int movieId){
    return movieRepository.getReviewsApi(movieId);
  }
  public LiveData<Resource<List<Trailer>>> getTrailersApi(int movieId){
    return movieRepository.getTrailersApi(movieId);
  }
}
