package com.nirmal.jeffrey.flickvibes.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.util.Constants;
import com.nirmal.jeffrey.flickvibes.viewmodel.MovieListViewModel;
import java.util.List;

i

public class MovieListActivity extends AppCompatActivity {

  private static final String TAG = "MovieListActivity";
  private MovieListViewModel movieListViewModel;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_movie);
    movieListViewModel= ViewModelProviders.of(this).get(MovieListViewModel.class);
    subscribeObservers();
    findViewById(R.id.click_button).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        testRetrofitResponses();
      }
    });
  }
  private void subscribeObservers(){
    movieListViewModel.getMoviesList().observe(this, new Observer<List<Movie>>() {
      @Override
      public void onChanged(List<Movie> movies) {
        if(movies!=null){
          for(Movie movie:movies){
            Log.d(TAG, "onChanged: "+movie.getTitle());
          }
        }

      }
    });
  }

    private void getMovieListApi(String pathType, int pageNumber){
      movieListViewModel.getMovieListApi(pathType,pageNumber);
    }


  private void testRetrofitResponses() {
    getMovieListApi(Constants.POPULAR_MOVIE_PATH,1);

  }
}

