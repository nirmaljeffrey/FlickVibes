package com.nirmal.jeffrey.flickvibes.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.adapter.MovieAdapter;
import com.nirmal.jeffrey.flickvibes.adapter.MovieAdapter.OnMovieItemClickLister;
import com.nirmal.jeffrey.flickvibes.emotiondetector.Emotions;
import com.nirmal.jeffrey.flickvibes.emotiondetector.GenreByEmotion;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.util.Constants;
import com.nirmal.jeffrey.flickvibes.viewmodel.MoviesByEmotionViewModel;
import java.util.ArrayList;

public class EmotionMoviesActivity extends BaseActivity implements OnMovieItemClickLister {

  @BindView(R.id.emotion_movies_recycler_view)
  RecyclerView recyclerView;
  private MoviesByEmotionViewModel moviesByEmotionViewModel;
  private MovieAdapter emotionAdapter;
  private Integer moviePositionInRecyclerView;
  private Integer selectedMovieId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_emotion_movies);
    setExitTransition();
    ButterKnife.bind(this);
    moviesByEmotionViewModel = ViewModelProviders.of(this).get(MoviesByEmotionViewModel.class);
    initRecyclerView();
    getIncomingIntent();
    subscribeObservers();

  }

  private void getIncomingIntent() {
    if (getIntent().hasExtra(Constants.EMOTION_ACTIVITY_INTENT)) {

      Emotions emotions = (Emotions) getIntent()
          .getSerializableExtra(Constants.EMOTION_ACTIVITY_INTENT);

      getMoviesByEmotionApi(GenreByEmotion.getGenreByEmotion(emotions));
      if (getSupportActionBar() != null) {
        getSupportActionBar().setTitle(
            getString(R.string.emotion_movie_activity_title, emotions.toString().toLowerCase()));
      }

    }
  }

  private void subscribeObservers() {
    moviesByEmotionViewModel.getMoviesByEmotion().observe(this,
        listResource -> {
          if (listResource != null) {
            if (listResource.data != null) {
              switch (listResource.status) {
                case LOADING:
                  displayLoading();
                  break;
                case ERROR:
                  displayError(listResource.message);
                  emotionAdapter.setMovieData(new ArrayList<>(listResource.data));
                  break;
                case SUCCESS:
                  displayMovies();
                  emotionAdapter.setMovieData(new ArrayList<>(listResource.data));
                  break;
              }

            }
          }
        });
  }

  private void initRecyclerView() {
    int spanCount = getResources().getInteger(R.integer.grid_span_count);
    recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
    emotionAdapter = new MovieAdapter(initGlide(), this);
    recyclerView.setAdapter(emotionAdapter);
  }

  private void displayLoading() {
    //Method from BaseActivity.java
    showProgressBar(true);
    recyclerView.setVisibility(View.GONE);
  }

  private void displayError(String message) {
    //Method from BaseActivity.java
    showProgressBar(false);
    recyclerView.setVisibility(View.VISIBLE);
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  private void displayMovies() {
    //Method from BaseActivity.java
    showProgressBar(false);
    recyclerView.setVisibility(View.VISIBLE);
  }

  private void getMoviesByEmotionApi(int genre) {
    moviesByEmotionViewModel.getMovieListByEmotionApi(genre, 1);

  }

  /**
   * Method for creating request manager for recycler view
   *
   * @return RequestManager (Glide) to be used in recycler view adapter
   */
  private RequestManager initGlide() {
    RequestOptions requestOptions = new RequestOptions()
        .error(R.drawable.poster_place_holder)
        .fallback(R.drawable.poster_place_holder);
    return Glide.with(this)
        .setDefaultRequestOptions(requestOptions);
  }

  @Override
  public void onClickItem(int position) {
    if (emotionAdapter.getMovieArrayList() != null) {
      Movie movie = emotionAdapter.getMovieArrayList().get(position);
      moviePositionInRecyclerView = position;
      selectedMovieId = movie.getId();
      Intent intent = new Intent(EmotionMoviesActivity.this, MovieDetailActivity.class);
      intent.putExtra(Constants.EMOTION_MOVIE_LIST_INTENT, movie);
      //Add activity options for activity transitions
      startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
  }


  @Override
  protected void onResume() {
    super.onResume();
    if (emotionAdapter.getMovieArrayList() != null) {
      if (moviePositionInRecyclerView != null && selectedMovieId != null) {
        LiveData<Movie> movieLiveData = moviesByEmotionViewModel.getMovie(selectedMovieId);
        movieLiveData.observe(this, new Observer<Movie>() {
          @Override
          public void onChanged(Movie movie) {
            movieLiveData.removeObserver(this);
            emotionAdapter.getMovieArrayList().set(moviePositionInRecyclerView, movie);
            emotionAdapter.notifyItemChanged(moviePositionInRecyclerView);
          }
        });
      }
    }

  }


  private void setExitTransition() {
    Fade exitTransition = new Fade();
    exitTransition.setDuration(300);
    getWindow().setExitTransition(exitTransition);
  }
}

