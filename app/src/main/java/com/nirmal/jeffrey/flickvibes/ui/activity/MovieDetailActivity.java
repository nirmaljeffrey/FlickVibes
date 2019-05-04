package com.nirmal.jeffrey.flickvibes.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.adapter.CastAdapter;
import com.nirmal.jeffrey.flickvibes.adapter.CastAdapter.CastClickListener;
import com.nirmal.jeffrey.flickvibes.adapter.GenreAdapter;
import com.nirmal.jeffrey.flickvibes.adapter.ReviewAdapter;
import com.nirmal.jeffrey.flickvibes.adapter.TrailerAdapter;
import com.nirmal.jeffrey.flickvibes.adapter.TrailerAdapter.TrailerClickListener;
import com.nirmal.jeffrey.flickvibes.model.Cast;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.model.Trailer;
import com.nirmal.jeffrey.flickvibes.util.Constants;
import com.nirmal.jeffrey.flickvibes.util.NetworkUtils;
import com.nirmal.jeffrey.flickvibes.viewmodel.MovieDetailViewModel;
import java.util.ArrayList;

public class MovieDetailActivity extends BaseActivity implements TrailerClickListener,
    CastClickListener {


  @BindView(R.id.movie_detail_coordinator_layout)
  CoordinatorLayout coordinatorLayout;
  @BindView(R.id.movie_detail_collapsing_tool_bar)
  CollapsingToolbarLayout collapsingToolbarLayout;
  @BindView(R.id.app_bar)
  AppBarLayout appBarLayout;
  @BindView(R.id.movie_detail_tool_bar)
  Toolbar toolbar;
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
  @BindView(R.id.favorite_fab_button)
  FloatingActionButton favoriteButton;
  @BindView(R.id.poster_image_view)
  ImageView posterImage;
  @BindView(R.id.view)
  View titleSpaceView;
  @BindView(R.id.genre_recycler_view)
  RecyclerView genreList;
  @BindView(R.id.trailer_recycler_view)
  RecyclerView trailerList;
  @BindView(R.id.cast_recycler_view)
  RecyclerView castList;
  @BindView(R.id.review_recycler_view)
  RecyclerView reviewList;
  @BindView(R.id.genre_label)
  TextView genreLabel;
  @BindView(R.id.trailer_label)
  TextView trailerLabel;
  @BindView(R.id.cast_label)
  TextView castLabel;
  @BindView(R.id.review_label)
  TextView reviewLabel;

  private Movie movie;
  private ReviewAdapter reviewAdapter;
  private CastAdapter castAdapter;
  private TrailerAdapter trailerAdapter;
  private GenreAdapter genreAdapter;
  private MovieDetailViewModel movieDetailViewModel;
  private boolean isFavorite;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);
    ButterKnife.bind(this);
    movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
    getIncomingIntent();
    setMoviePropertiesToWidgets();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      // Widget Item is clicked and opens a movieDetailActivity, at this point when the Up button is pressed,
      // the app moves to mainActivity and kills the movieDetailActivity, instead of closing the app.
      if (getIntent() != null && getIntent().getStringExtra(Constants.WIDGET_INTENT_IDENTIFIER)
          .equals(Constants.WIDGET_CLASS_NAME)) {
        Intent intent = new Intent(this, MovieListActivity.class);
        startActivity(intent);
        finish();
      } else {
        finish();
      }
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void initRecyclerViews() {
    //Reviews
    reviewAdapter = new ReviewAdapter();
    reviewList.setAdapter(reviewAdapter);
    reviewList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    //Cast
    castAdapter = new CastAdapter(initGlide(), MovieDetailActivity.this);
    castList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    castList.setAdapter(castAdapter);
    //Trailer
    trailerAdapter = new TrailerAdapter(initGlide(), MovieDetailActivity.this);

    trailerList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    trailerList.setAdapter(trailerAdapter);
    //Genre
    genreAdapter = new GenreAdapter();
    genreList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    genreList.setAdapter(genreAdapter);
  }

  private void initFavoriteFab(int movieId) {
    favoriteButton.setOnClickListener(view -> {
      if (!isFavorite) {
        favoriteButton.setImageResource(R.drawable.ic_menu_favorite_24dp);
        movieDetailViewModel.setMovieAsFavorite(movieId);
        snackBarMessage(favoriteButton, getString(R.string.snackbar_movie_added));
        isFavorite = true;
      } else {
        favoriteButton.setImageResource(R.drawable.ic_favorite_border_24dp);
        movieDetailViewModel.removeMovieFromFavorite(movieId);
        snackBarMessage(favoriteButton, getString(R.string.snackbar_movie_removed));
        isFavorite = false;
      }
    });
  }

  private void subscribeObservers(int movieId) {
    movieDetailViewModel.getReviewsApi(movieId).observe(this,
        listResource -> {
          if (listResource != null) {
            if (listResource.data != null) {
              switch (listResource.status) {
                case LOADING:
                  showProgressBar(true);
                  break;
                case ERROR:
                  coordinatorLayout.setVisibility(View.VISIBLE);
                  showProgressBar(false);
                  toastMessage(listResource.message);
                  if (listResource.data.isEmpty()) {
                    setReviewVisibility(false);
                  } else {
                    setReviewVisibility(true);
                    reviewAdapter.setReviewData(new ArrayList<>(listResource.data));
                  }
                  break;
                case SUCCESS:
                  coordinatorLayout.setVisibility(View.VISIBLE);
                  showProgressBar(false);
                  if (listResource.data.isEmpty()) {
                    setReviewVisibility(false);
                  } else {
                    setReviewVisibility(true);
                    reviewAdapter.setReviewData(new ArrayList<>(listResource.data));
                  }
                  break;
              }

            }
          }
        });
    movieDetailViewModel.getTrailersApi(movieId).observe(this,
        listResource -> {
          if (listResource != null) {
            if (listResource.data != null) {
              switch (listResource.status) {
                case LOADING:
                  showProgressBar(true);
                  coordinatorLayout.setVisibility(View.INVISIBLE);
                  break;
                case ERROR:
                  coordinatorLayout.setVisibility(View.VISIBLE);
                  showProgressBar(false);
                  toastMessage(listResource.message);
                  if (listResource.data.isEmpty()) {
                    setTrailerVisibility(false);
                  } else {
                    setTrailerVisibility(true);
                    trailerAdapter.setTrailerData(new ArrayList<>(listResource.data));
                  }
                  break;
                case SUCCESS:
                  coordinatorLayout.setVisibility(View.VISIBLE);
                  showProgressBar(false);
                  if (listResource.data.isEmpty()) {
                    setTrailerVisibility(false);
                  } else {
                    setTrailerVisibility(true);
                    trailerAdapter.setTrailerData(new ArrayList<>(listResource.data));
                  }
                  break;
              }

            }
          }
        });
    movieDetailViewModel.getCastApi(movieId).observe(this, listResource -> {
      if (listResource != null) {
        if (listResource.data != null) {
          switch (listResource.status) {
            case LOADING:
              showProgressBar(true);
              coordinatorLayout.setVisibility(View.INVISIBLE);
              break;
            case ERROR:
              coordinatorLayout.setVisibility(View.VISIBLE);
              showProgressBar(false);
              toastMessage(listResource.message);
              if (listResource.data.isEmpty()) {
                setCastVisibility(false);
              } else {
                setCastVisibility(true);
                castAdapter.setCastData(new ArrayList<>(listResource.data));
              }
              break;
            case SUCCESS:
              coordinatorLayout.setVisibility(View.VISIBLE);
              showProgressBar(false);
              if (listResource.data.isEmpty()) {
                setCastVisibility(false);
              } else {
                setCastVisibility(true);
                castAdapter.setCastData(new ArrayList<>(listResource.data));
              }
              break;
          }

        }
      }
    });
    movieDetailViewModel.getGenresApi(movieId).observe(this, listResource -> {
      if (listResource != null) {
        if (listResource.data != null) {

          switch (listResource.status) {
            case LOADING:
              showProgressBar(true);
              coordinatorLayout.setVisibility(View.INVISIBLE);
              break;
            case ERROR:
              coordinatorLayout.setVisibility(View.VISIBLE);
              showProgressBar(false);
              toastMessage(listResource.message);
              if (listResource.data.isEmpty()) {
                setGenreVisibility(false);
              } else {
                setGenreVisibility(true);
                genreAdapter.setGenreData(new ArrayList<>(listResource.data));
              }
              break;
            case SUCCESS:
              coordinatorLayout.setVisibility(View.VISIBLE);
              showProgressBar(false);
              if (listResource.data.isEmpty()) {
                setGenreVisibility(false);
              } else {
                setGenreVisibility(true);
                genreAdapter.setGenreData(new ArrayList<>(listResource.data));
              }
              break;
          }

        }
      }
    });
    //Setup favorite floating action button
    movieDetailViewModel.getFavoriteMovie(movieId).observe(this, movie -> {

      if (movie != null && movie.isFavorite()) {
        favoriteButton.setImageResource(R.drawable.ic_menu_favorite_24dp);
        isFavorite = true;

      } else {

        isFavorite = false;
        favoriteButton.setImageResource(R.drawable.ic_favorite_border_24dp);
      }
    });
  }


  private void getIncomingIntent() {
    if (getIntent() != null) {
      if (getIntent().hasExtra(Constants.MOVIE_LIST_INTENT)) {
        movie = getIntent().getParcelableExtra(Constants.MOVIE_LIST_INTENT);

      } else if (getIntent().hasExtra(Constants.EMOTION_MOVIE_LIST_INTENT)) {
        movie = getIntent().getParcelableExtra(Constants.EMOTION_MOVIE_LIST_INTENT);
      }

      subscribeObservers(movie.getId());
      initFavoriteFab(movie.getId());
    }
  }

  private void setMoviePropertiesToWidgets() {
    collapsingToolbarLayout.setTitle(movie.getTitle());
    titleTextView.setText(movie.getTitle());
    releaseDateTextView.setText(movie.getReleaseDate());
    ratingTextView.setText(getString(R.string.vote_average_format, movie.getVoteAverage()));
    storyLineTextView.setText(movie.getOverview());
    String backdropUrl = NetworkUtils
        .buildMovieImageURLString(NetworkUtils.BACKDROP_BASE_URL, movie.getBackdropPath());
    String posterUrl = NetworkUtils
        .buildMovieImageURLString(NetworkUtils.POSTER_BASE_URL, movie.getPosterPath());
    setImageUsingGlide(backdropUrl, backdropImage);
    setImageUsingGlide(posterUrl, posterImage);
    initToolbar();
    initRecyclerViews();
    initAppBarScrollAnimations();

    coordinatorLayout.setVisibility(View.VISIBLE);
    showProgressBar(false);

  }

  private void initToolbar() {
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
      collapsingToolbarLayout
          .setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
      collapsingToolbarLayout
          .setCollapsedTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
    }

  }

  private void initAppBarScrollAnimations() {
    appBarLayout.addOnOffsetChangedListener((AppBarLayout appBarLayout, int verticalOffset) -> {
      if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
        posterImage.setVisibility(View.GONE);
        titleSpaceView.setVisibility(View.GONE);
      } else {
        posterImage.setVisibility(View.VISIBLE);
        titleSpaceView.setVisibility(View.VISIBLE);
      }
    });
  }

  private void setImageUsingGlide(String url, ImageView imageView) {
    RequestOptions requestOptions = new RequestOptions()
        .error(R.drawable.poster_place_holder)
        .fallback(R.drawable.poster_place_holder);
    Glide.with(this)
        .setDefaultRequestOptions(requestOptions)
        .load(url)
        .centerCrop()
        .into(imageView);
  }

  private void snackBarMessage(View view, String message) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
  }

  /**
   * Method for creating request manager for recycler view
   *
   * @return RequestManager (Glide)
   */
  private RequestManager initGlide() {
    RequestOptions requestOptions = new RequestOptions()
        .error(R.drawable.poster_place_holder)
        .fallback(R.drawable.poster_place_holder);
    return Glide.with(this)
        .setDefaultRequestOptions(requestOptions);

  }

  private void toastMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  private void setCastVisibility(boolean visibility) {
    castList.setVisibility(visibility ? View.VISIBLE : View.GONE);
    castLabel.setVisibility(visibility ? View.VISIBLE : View.GONE);
  }

  private void setGenreVisibility(boolean visibility) {
    genreList.setVisibility(visibility ? View.VISIBLE : View.GONE);
    genreLabel.setVisibility(visibility ? View.VISIBLE : View.GONE);
  }

  private void setTrailerVisibility(boolean visibility) {
    trailerList.setVisibility(visibility ? View.VISIBLE : View.GONE);
    trailerLabel.setVisibility(visibility ? View.VISIBLE : View.GONE);
  }

  private void setReviewVisibility(boolean visibility) {
    reviewList.setVisibility(visibility ? View.VISIBLE : View.GONE);
    reviewLabel.setVisibility(visibility ? View.VISIBLE : View.GONE);
  }

  @Override
  public void onTrailerVideoClick(Trailer trailer) {
    Uri youtubeAppUri = NetworkUtils.buildYoutubeAppVideoUrl(trailer.getKey());
    Intent youtubeAppIntent = new Intent(Intent.ACTION_VIEW, youtubeAppUri);
    Uri youtubeWebUri = NetworkUtils.buildYoutubeWebVideoUrl(trailer.getKey());
    Intent youtubeWebIntent = new Intent(Intent.ACTION_VIEW, youtubeWebUri);
    if (youtubeAppIntent.resolveActivity(getPackageManager()) != null) {
      startActivity(youtubeAppIntent);
    } else {
      startActivity(youtubeWebIntent);
    }
  }

  @Override
  public void onTrailerShareClick(Trailer trailer) {
    String youtubeShareUri = NetworkUtils.buildYoutubeWebVideoUrl(trailer.getKey()).toString();
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.setType(NetworkUtils.TRAILER_MIME_TYPE);
    shareIntent.putExtra(Intent.EXTRA_TEXT, youtubeShareUri);
    startActivity(
        Intent.createChooser(shareIntent, getString(R.string.trailer_share_intent_title)));
  }

  @Override
  public void onCastItemClick(Cast cast) {
    Uri castUri = NetworkUtils.buildCastWebUrl(cast.getCastName());
    Intent castIntent = new Intent(Intent.ACTION_VIEW, castUri);
    try {
      startActivity(castIntent);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onBackPressed() {
    // Widget Item is clicked and opens a movieDetailActivity, at this point when the back button is pressed,
    // the app moves to mainActivity and kills the movieDetailActivity, instead of closing the app.
    if (getIntent() != null && getIntent().getStringExtra(Constants.WIDGET_INTENT_IDENTIFIER)
        .equals(Constants.WIDGET_CLASS_NAME)) {
      Intent intent = new Intent(this, MovieListActivity.class);
      startActivity(intent);
      finish();
    }else {
      super.onBackPressed();
    }
  }
}
