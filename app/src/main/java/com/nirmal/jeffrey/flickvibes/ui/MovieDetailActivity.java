package com.nirmal.jeffrey.flickvibes.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
import com.nirmal.jeffrey.flickvibes.adapter.SimilarMoviesAdapter;
import com.nirmal.jeffrey.flickvibes.adapter.TrailerAdapter;
import com.nirmal.jeffrey.flickvibes.model.Genre;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.model.Trailer;
import com.nirmal.jeffrey.flickvibes.util.Constants;
import com.nirmal.jeffrey.flickvibes.util.NetworkUtils;
import com.nirmal.jeffrey.flickvibes.viewmodel.MovieDetailViewModel;
import java.util.ArrayList;

public class MovieDetailActivity extends BaseActivity {

  private static final String TAG = "MovieId";
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
private SimilarMoviesAdapter similarMoviesAdapter;
private MovieDetailViewModel movieDetailViewModel;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);
    ButterKnife.bind(this);
    movieDetailViewModel= ViewModelProviders.of(this).get(MovieDetailViewModel.class);
    getIncomingIntent();
    setMoviePropertiesToWidgets();




  }
  private void initRecyclerViews(){

    //Reviews
    reviewAdapter = new ReviewAdapter();
    reviewList.setAdapter(reviewAdapter);
    reviewList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    //Cast
    castAdapter=new CastAdapter(initGlide());
    castList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
    castList.setAdapter(castAdapter);
    //Trailer
    trailerAdapter=new TrailerAdapter(initGlide());

    trailerList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
    trailerList.setAdapter(trailerAdapter);
    //Genre
    genreAdapter = new GenreAdapter();
    genreList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
    genreList.setAdapter(genreAdapter);
    //Similar movies
    similarMoviesAdapter=new SimilarMoviesAdapter(initGlide());
    similarMovieList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
    similarMovieList.setAdapter(similarMoviesAdapter);
  }
  private void subscribeObservers(int movieId){
    movieDetailViewModel.getReviewsApi(movieId).observe(this,
        listResource -> {
          if(listResource!=null){
            if(listResource.data!=null){
              switch (listResource.status){
                case LOADING:
                  showProgressBar(true);
                  break;
                case ERROR:
                  coordinatorLayout.setVisibility(View.VISIBLE);
                  showProgressBar(false);
                  toastMessage(listResource.message);
                  reviewAdapter.setReviewData(new ArrayList<>(listResource.data));


                  break;
                case SUCCESS:
                  coordinatorLayout.setVisibility(View.VISIBLE);
                  showProgressBar(false);
                  reviewAdapter.setReviewData(new ArrayList<>(listResource.data));


                  break;
              }
            }
          }
        });
    movieDetailViewModel.getTrailersApi(movieId).observe(this,
        listResource -> {
          if(listResource!=null){
            if(listResource.data!=null){
              switch (listResource.status){
                case LOADING:
                  showProgressBar(true);
                  coordinatorLayout.setVisibility(View.INVISIBLE);
                  break;
                case ERROR:
                  coordinatorLayout.setVisibility(View.VISIBLE);
                  showProgressBar(false);
                  toastMessage(listResource.message);
                  ArrayList<Trailer> trailerList =new ArrayList<>(listResource.data);
                  for (Trailer trailer:trailerList){
                    Log.d(TAG, "subscribeObservers: error :trailer Name "+ trailer.getName());
                  }
                  trailerAdapter.setTrailerData(new ArrayList<>(listResource.data));

                  break;
                case SUCCESS:
                  coordinatorLayout.setVisibility(View.VISIBLE);
                  showProgressBar(false);
                  ArrayList<Trailer> trailers =new ArrayList<>(listResource.data);
                  for (Trailer trailer:trailers){
                    Log.d(TAG, "subscribeObservers: success :trailer Name "+ trailer.getName());

                  }
                  trailerAdapter.setTrailerData(new ArrayList<>(listResource.data));

                  break;
              }
            }
          }
        });
    movieDetailViewModel.getCastApi(movieId).observe(this, listResource -> {
      if(listResource!=null){
        if(listResource.data!=null){
          switch (listResource.status){
            case LOADING:
              showProgressBar(true);
              coordinatorLayout.setVisibility(View.INVISIBLE);
              break;
            case ERROR:
              coordinatorLayout.setVisibility(View.VISIBLE);
              showProgressBar(false);
              toastMessage(listResource.message);
              castAdapter.setCastData(new ArrayList<>(listResource.data));

              break;
            case SUCCESS:
              coordinatorLayout.setVisibility(View.VISIBLE);
              showProgressBar(false);
              castAdapter.setCastData(new ArrayList<>(listResource.data));
              break;
          }
        }
      }
    });
    movieDetailViewModel.getGenresApi(movieId).observe(this, listResource -> {
      if(listResource!=null){
        if(listResource.data!=null){
          switch (listResource.status){
            case LOADING:
              showProgressBar(true);
              coordinatorLayout.setVisibility(View.INVISIBLE);
              break;
            case ERROR:
              coordinatorLayout.setVisibility(View.VISIBLE);
              showProgressBar(false);
              toastMessage(listResource.message);
              ArrayList<Genre> genreList =new ArrayList<>(listResource.data);
              for (Genre genre:genreList){
                Log.d(TAG, "subscribeObservers: error :trailer Name "+ genre.getGenreName());
              }
              genreAdapter.setGenreData(new ArrayList<>(listResource.data));

              break;
            case SUCCESS:
              coordinatorLayout.setVisibility(View.VISIBLE);
              showProgressBar(false);
              ArrayList<Genre> genres =new ArrayList<>(listResource.data);
              for (Genre genre:genres){
                Log.d(TAG, "subscribeObservers: success :trailer Name "+ genre.getGenreName());
              }
              genreAdapter.setGenreData(new ArrayList<>(listResource.data));
              break;
          }
        }
      }
    });
  }

  private void getIncomingIntent(){
    if(getIntent().hasExtra(Constants.MOVIE_LIST_INTENT)){
       movie= getIntent().getParcelableExtra(Constants.MOVIE_LIST_INTENT);
      Log.d(TAG, "movieDetail: movieId"+movie.getId());
      Log.d(TAG, "movieDetail: movieId"+movie.getPosterPath());
      subscribeObservers(movie.getId());
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
  private void toastMessage(String message){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
  }
}
