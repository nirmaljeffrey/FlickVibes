package com.nirmal.jeffrey.flickvibes.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.util.Constants;
import com.nirmal.jeffrey.flickvibes.util.Resource;
import com.nirmal.jeffrey.flickvibes.viewmodel.MovieListViewModel;
import java.util.List;

public class MovieListActivity extends BaseActivity {
  @BindView(R.id.movies_recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.bottom_navigation)
  BottomNavigationView bottomNavigationBar;

  private static final String TAG = "MovieListActivity";
  private MovieListViewModel movieListViewModel;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_movie);
    ButterKnife.bind(this);
    movieListViewModel= ViewModelProviders.of(this).get(MovieListViewModel.class);
    subscribeObservers();
    bottomNavigationBar.setOnNavigationItemSelectedListener(navListener);

  }

  private BottomNavigationView.OnNavigationItemSelectedListener navListener = new OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
      switch (menuItem.getItemId()){
        case R.id.nav_popular:
          getMovieListByTypeApi(Constants.POPULAR_MOVIE_PATH);
          break;
        case R.id.nav_top_rated:
          getMovieListByTypeApi(Constants.TOP_RATER_MOVIE_PATH);
          break;
        case R.id.nav_Upcoming:
          getMovieListByTypeApi(Constants.UPCOMING_MOVIE_PATH);
          break;
        case R.id.nav_now_playing:
          getMovieListByTypeApi(Constants.NOW_PLAYING_MOVIE_PATH);
          break;
        case R.id.nav_favorites:
          break;

      }
      return true;
    }
  };
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

