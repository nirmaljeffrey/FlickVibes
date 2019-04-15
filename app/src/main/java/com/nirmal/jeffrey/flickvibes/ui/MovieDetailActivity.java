package com.nirmal.jeffrey.flickvibes.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.adapter.CastAdapter;
import com.nirmal.jeffrey.flickvibes.adapter.GenreAdapter;
import com.nirmal.jeffrey.flickvibes.adapter.ReviewAdapter;
import com.nirmal.jeffrey.flickvibes.adapter.TrailerAdapter;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.util.Constants;
import com.nirmal.jeffrey.flickvibes.util.NetworkUtils;
import com.nirmal.jeffrey.flickvibes.viewmodel.MovieDetailViewModel;

public class MovieDetailActivity extends BaseActivity {
  @BindView(R.id.movie_detail_coordinator_layout)
  CoordinatorLayout coordinatorLayout;
@BindView(R.id.back_drop_image_view)
  ImageView backdropImage;
@BindView(R.id.movie_title)
  TextView titleTextView;
@BindView(R.id.release_date_text_view)
TextView releaseDateTextView;
@BindView(R.id.rating_text_view)
TextView ratingTextView;
@BindView(R.id.storyline_text_view)
TextView storyLineTextView;
@BindView(R.id.genre_recycler_view)
  RecyclerView genreList;
@BindView(R.id.trailer_recycler_view)
RecyclerView trailerList;
@BindView(R.id.cast_recycler_view)
RecyclerView castList;
@BindView(R.id.similar_movies_recycler_view)
RecyclerView similarMovieList;
@BindView(R.id.review_recycler_view)
RecyclerView reviewList;
@BindView(R.id.favorite_fab_button)
FloatingActionButton favoriteButton;
@BindView(R.id.poster_image_view)
ImageView posterImage;
private Movie movie;
private ReviewAdapter reviewAdapter;
private CastAdapter castAdapter;
private TrailerAdapter trailerAdapter;
private GenreAdapter genreAdapter;
private MovieDetailViewModel movieDetailViewModel;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);
    ButterKnife.bind(this);
    movieDetailViewModel= ViewModelProviders.of(this).get(MovieDetailViewModel.class);
    getIncomingIntent();
    showProgressBar(true);
    coordinatorLayout.setVisibility(View.INVISIBLE);
    setMoviePropertiesToWidgets();

  }
  private void initRecyclerViews(){
    reviewAdapter = new ReviewAdapter();
    reviewList.setAdapter(reviewAdapter);
    reviewList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
    castAdapter=new CastAdapter(initGlide());
    castList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
    castList.setAdapter(castAdapter);
    trailerAdapter=new TrailerAdapter(initGlide());
    trailerList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
    trailerList.setAdapter(trailerAdapter);
    genreAdapter = new GenreAdapter();
    genreList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
    genreList.setAdapter(genreAdapter);
  }
  private void subscribeObservers(){

  }

  private void getIncomingIntent(){
    if(getIntent().hasExtra(Constants.MOVIE_LIST_INTENT)){
       movie= getIntent().getParcelableExtra(Constants.MOVIE_LIST_INTENT);
    }
  }
  private void setMoviePropertiesToWidgets(){
    titleTextView.setText(movie.getTitle());
    releaseDateTextView.setText(movie.getReleaseDate());
    ratingTextView.setText(String.valueOf(movie.getVoteAverage()));
    storyLineTextView.setText(movie.getOverview());
    String backdropUrl = NetworkUtils.buildMovieImageURLString(NetworkUtils.BACKDROP_BASE_URL,movie.getBackdropPath());
    String posterUrl =NetworkUtils.buildMovieImageURLString(NetworkUtils.POSTER_BASE_URL,movie.getPosterPath());
    setImageUsingGlide(backdropUrl,backdropImage);
    setImageUsingGlide(posterUrl,posterImage);
    initRecyclerViews();
    coordinatorLayout.setVisibility(View.VISIBLE);
    showProgressBar(false);

  }

  private void setImageUsingGlide(String url,ImageView imageView){
    RequestOptions requestOptions = new RequestOptions()
        .error(R.drawable.poster_place_holder)
        .fallback(R.drawable.poster_place_holder);
     Glide.with(this)
        .setDefaultRequestOptions(requestOptions)
         .load(url)
         .centerCrop()
         .into(imageView);


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
}
