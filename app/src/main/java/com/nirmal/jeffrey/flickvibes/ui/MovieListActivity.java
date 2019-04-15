package com.nirmal.jeffrey.flickvibes.ui;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.adapter.MovieAdapter;
import com.nirmal.jeffrey.flickvibes.adapter.MovieAdapter.OnMovieItemClickLister;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.util.Constants;
import com.nirmal.jeffrey.flickvibes.util.NetworkUtils;
import com.nirmal.jeffrey.flickvibes.util.Resource;
import com.nirmal.jeffrey.flickvibes.viewmodel.MovieListViewModel;
import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends BaseActivity implements OnMovieItemClickLister {

  private static final String TAG = "MovieListActivity";
  @BindView(R.id.error_text_view)
  TextView errorTextView;
  @BindView(R.id.movies_recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.bottom_navigation)
  BottomNavigationView bottomNavigationBar;
  private MovieAdapter movieAdapter;
  private MovieListViewModel movieListViewModel;
  private BottomNavigationView.OnNavigationItemSelectedListener navListener = new OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
      switch (menuItem.getItemId()) {
        case R.id.nav_popular:
          getMovieListByTypeApi(NetworkUtils.POPULAR_MOVIE_PATH);
          break;
        case R.id.nav_top_rated:
          getMovieListByTypeApi(NetworkUtils.TOP_RATER_MOVIE_PATH);
          break;
        case R.id.nav_Upcoming:
          getMovieListByTypeApi(NetworkUtils.UPCOMING_MOVIE_PATH);
          break;
        case R.id.nav_now_playing:
          getMovieListByTypeApi(NetworkUtils.NOW_PLAYING_MOVIE_PATH);
          break;
        case R.id.nav_favorites:
          break;

      }
      return true;
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_movie);
    ButterKnife.bind(this);
    initRecyclerView();
    bottomNavigationBar.setOnNavigationItemSelectedListener(navListener);
    movieListViewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
    subscribeObservers();

    getMovieListByTypeApi(NetworkUtils.POPULAR_MOVIE_PATH);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.movie_list_activity_menu,menu);
    MenuItem searchItem = menu.findItem(R.id.action_search);
    SearchView searchView= (SearchView)searchItem.getActionView();
    searchView.setQueryHint(getString(R.string.menu_search_hint));
    searchView.setIconified(false);
    searchView.setOnQueryTextListener(new OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String s) {
        getMovieListFromSearch(s);
        return true;
      }

      @Override
      public boolean onQueryTextChange(String s) {
        return false;
      }
    });
    return true;
  }

  private void subscribeObservers() {
    movieListViewModel.getMoviesFromSearch().observe(this, listResource -> {
      if(listResource!=null){
        if(listResource.data!=null){
          switch (listResource.status){
            case LOADING:
              displayLoading();
              break;
            case ERROR:
              displayError(listResource.message);
              movieAdapter.setMovieData(new ArrayList<>(listResource.data));
              break;
            case SUCCESS:
              displayMovies();
              movieAdapter.setMovieData(new ArrayList<>(listResource.data));
              break;
          }

        }
      }
    });
    movieListViewModel.getMoviesByType().observe(this, (Resource<List<Movie>> listResource) -> {
      if (listResource != null) {

        if (listResource.data != null) {

          switch (listResource.status){
            case LOADING:
              Log.d(TAG, "subscribeObservers: ApiLoading");
             displayLoading();
              break;
            case ERROR:
              Log.d(TAG, "subscribeObservers: ApiError");
              displayError(listResource.message);
              movieAdapter.setMovieData(new ArrayList<>(listResource.data));


              break;
            case SUCCESS:
              Log.d(TAG, "subscribeObservers: APiSuccess");
              displayMovies();
              movieAdapter.setMovieData(new ArrayList<>(listResource.data));

              break;
          }



        }

      }
    });

  }
  private void displayLoading(){
    //Method from BaseActivity.java
    showProgressBar(true);
    recyclerView.setVisibility(View.GONE);

  }
  private void displayError(String message){
    //Method from BaseActivity.java
    showProgressBar(false);
    recyclerView.setVisibility(View.VISIBLE);
    toastMessage(message);
  }
  private void displayMovies(){
    //Method from BaseActivity.java
    showProgressBar(false);
    recyclerView.setVisibility(View.VISIBLE);
  }

  private void toastMessage(String message){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
  }



  private void initRecyclerView(){
    int spanCount = getResources().getInteger(R.integer.grid_span_count);
    recyclerView.setLayoutManager(new GridLayoutManager(this,spanCount));
    movieAdapter = new MovieAdapter(initGlide(),this);
    recyclerView.setAdapter(movieAdapter);
  }

  private void getMovieListByTypeApi(String type) {
    movieListViewModel.getMovieListByTypeApi(type, 1);
  }
  private void getMovieListFromSearch(String query){
    movieListViewModel.getMovieListFromSearchApi(query,1);
  }

  /**
   * Method for creating request manager for recycler view
   * @return RequestManager (Glide)
   */
  private RequestManager initGlide() {
    RequestOptions requestOptions = new RequestOptions()
        .error(R.drawable.poster_place_holder)
        .fallback(R.drawable.poster_place_holder);
    return Glide.with(this)
        .setDefaultRequestOptions(requestOptions);

  }


  @Override
  public void onClickItem(Movie movie) {
    Intent intent = new Intent(MovieListActivity.this,MovieDetailActivity.class);
    intent.putExtra(Constants.MOVIE_LIST_INTENT,movie);
    startActivity(intent);
  }


}

