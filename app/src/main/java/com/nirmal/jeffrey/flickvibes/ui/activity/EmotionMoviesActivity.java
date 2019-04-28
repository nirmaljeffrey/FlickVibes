package com.nirmal.jeffrey.flickvibes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
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
  private MoviesByEmotionViewModel moviesByEmotionViewModel;
  @BindView(R.id.emotion_movies_recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.emotion_error_text_view)
  TextView emotionErrorTextView;
  private MovieAdapter emotionAdapter;


  private static final String TAG = "EmotionMoviesActivity";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_emotion_movies);
    ButterKnife.bind(this);

    moviesByEmotionViewModel= ViewModelProviders.of(this).get(MoviesByEmotionViewModel.class);
    initRecyclerView();
    getIncomingIntent();
    subscribeObservers();
  }
  private void getIncomingIntent(){
    if(getIntent().hasExtra(Constants.EMOTION_ACTIVITY_INTENT)){

      Emotions emotions =(Emotions) getIntent().getSerializableExtra(Constants.EMOTION_ACTIVITY_INTENT);
      Log.d(TAG, "getIncomingIntent: EmotionType" + emotions.name());
      getMoviesByEmotionApi(GenreByEmotion.getGenreByEmotion(emotions));
      if(getSupportActionBar()!=null){
        getSupportActionBar().setTitle(getString(R.string.emotion_movie_activity_title,emotions.toString().toLowerCase()));
      }

    }
  }

  private void subscribeObservers(){
    moviesByEmotionViewModel.getMoviesByEmotion().observe(this,
        listResource -> {
          if(listResource!=null){
            if(listResource.data!=null){
              switch (listResource.status){
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
  private void initRecyclerView(){
    int spanCount = getResources().getInteger(R.integer.grid_span_count);
    recyclerView.setLayoutManager(new GridLayoutManager(this,spanCount));
    emotionAdapter = new MovieAdapter(initGlide(),this);
    recyclerView.setAdapter(emotionAdapter);
  }
  private void displayLoading(){
    //Method from BaseActivity.java

    showProgressBar(true);
    recyclerView.setVisibility(View.GONE);

  }
private void displayError(String message) {
  //Method from BaseActivity.java
  showProgressBar(false);
  recyclerView.setVisibility(View.VISIBLE);
  Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

}
private void displayMovies(){
  //Method from BaseActivity.java
  showProgressBar(false);
  recyclerView.setVisibility(View.VISIBLE);
}
public void getMoviesByEmotionApi(int genre){
    moviesByEmotionViewModel.getMovieListByEmotionApi(genre,1);

  }
  /**
   * Method for creating request manager for recycler view
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
  public void onClickItem(Movie movie) {
      Intent intent = new Intent(EmotionMoviesActivity.this, MovieDetailActivity.class);
      intent.putExtra(Constants.EMOTION_MOVIE_LIST_INTENT, movie);
      startActivity(intent);
    }
  }

