package com.nirmal.jeffrey.flickvibes.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.util.Resource;
import com.nirmal.jeffrey.flickvibes.viewmodel.MovieListViewModel;
import java.util.List;



public class MovieListActivity extends BaseActivity {

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

      }
    });
  }
  private void subscribeObservers(){
    movieListViewModel.getMovies().observe(this, new Observer<Resource<List<Movie>>>() {
      @Override
      public void onChanged(@Nullable Resource<List<Movie>> listResource) {
        if(listResource!=null){
          Log.d(TAG, "onChanged: "+listResource.status);
          if(listResource.data!=null){
            for(Movie movie: listResource.data){
              Log.d(TAG, "onChanged: " + movie.getTitle());
            }

          }

        }
      }
    });

  }

  private void getMovieListByTypeApi(String type){
    movieListViewModel.getMovieListByTypeApi(type,1);
  }




}

