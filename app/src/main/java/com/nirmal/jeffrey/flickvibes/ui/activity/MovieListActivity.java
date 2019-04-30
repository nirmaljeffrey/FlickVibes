package com.nirmal.jeffrey.flickvibes.ui.activity;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.ui.fragment.MovieListFragment;
import com.nirmal.jeffrey.flickvibes.util.BitmapUtils;
import com.nirmal.jeffrey.flickvibes.util.Constants;
import com.nirmal.jeffrey.flickvibes.util.NetworkUtils;
import com.nirmal.jeffrey.flickvibes.util.Resource;
import com.nirmal.jeffrey.flickvibes.viewmodel.MovieListViewModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends BaseActivity implements OnBackStackChangedListener {

  private static final String FILE_PROVIDER_AUTHORITY = "com.nirmal.jeffrey.flickvibes.fileprovider";
  private static final int REQUEST_PHOTO_PICKER = 1;
  private static final int REQUEST_IMAGE_CAPTURE = 2;
  private static final int REQUEST_STORAGE_PERMISSION = 3;
  private static final String SEARCH_FRAGMENT_TAG = "search_fragment_tag";
  @BindView(R.id.bottom_navigation)
  BottomNavigationView bottomNavigationBar;
  @BindView(R.id.prediction_fab)
  FloatingActionButton predictionFab;
  @BindView(R.id.movie_list_fragment)
  FrameLayout movieListContainer;
  private String cameraImagePath;
  private FragmentManager fragmentManager;

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
          LiveData<List<Movie>> movieList = movieListViewModel.getFavoriteMovies();
          movieList.observe(MovieListActivity.this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
              MovieListFragment movieListFragment = MovieListFragment
                  .getInstance(new ArrayList<>(movies), Constants.MOVIES_FROM_FAVORITES);
              loadFragment(movieListFragment, null);
              movieList.removeObserver(this);
            }
          });
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
    bottomNavigationBar.setOnNavigationItemSelectedListener(navListener);
    initFab();
    initFragmentManager();
    movieListViewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
    subscribeObservers();
    getMovieListByTypeApi(NetworkUtils.POPULAR_MOVIE_PATH);
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (bottomNavigationBar.getSelectedItemId() == R.id.nav_favorites) {
      LiveData<List<Movie>> moviesList = movieListViewModel.getFavoriteMovies();
      moviesList.observe(MovieListActivity.this, new Observer<List<Movie>>() {
        @Override
        public void onChanged(List<Movie> movies) {
          MovieListFragment movieListFragment = MovieListFragment
              .getInstance(new ArrayList<>(movies), Constants.MOVIES_FROM_FAVORITES);
          loadFragment(movieListFragment, null);
          moviesList.removeObserver(this);
        }
      });
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      if (requestCode == REQUEST_PHOTO_PICKER) {
        if (data != null) {
          Uri selectedImageUri = data.getData();
          Intent intent = new Intent(this, MoviePredictionActivity.class);
          if (selectedImageUri != null) {
            intent.putExtra(Constants.GALLERY_ACTIVITY_INTENT, selectedImageUri);
            startActivity(intent);
          }
        }
      } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
        Intent intent = new Intent(this, MoviePredictionActivity.class);
        if (cameraImagePath != null && !cameraImagePath.isEmpty()) {
          intent.putExtra(Constants.CAMERA_ACTIVITY_INTENT, cameraImagePath);
          startActivity(intent);
        }
      }
    }
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.movie_list_activity_menu, menu);
    MenuItem searchItem = menu.findItem(R.id.action_search);
    SearchView searchView = (SearchView) searchItem.getActionView();
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
    searchItem.setOnActionExpandListener(new OnActionExpandListener() {
      @Override
      public boolean onMenuItemActionExpand(MenuItem menuItem) {
        return true;
      }

      @Override
      public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        if (fragmentManager.getBackStackEntryCount() > 0) {
          fragmentManager
              .popBackStack(SEARCH_FRAGMENT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        return true;
      }
    });
    return true;
  }

  private void initFragmentManager() {
    fragmentManager = getSupportFragmentManager();
    fragmentManager.addOnBackStackChangedListener(MovieListActivity.this);
  }

  private void loadFragment(MovieListFragment fragment, String tagForBackStack) {
    if (fragment != null) {
      FragmentTransaction transaction = fragmentManager.beginTransaction();
      if (tagForBackStack == null) {

        transaction.replace(R.id.movie_list_fragment, fragment)
            .commit();
      } else {
        transaction.replace(R.id.movie_list_fragment, fragment)
            .addToBackStack(tagForBackStack)
            .commit();
      }

    }
  }

  private void subscribeObservers() {
    movieListViewModel.getMoviesFromSearch().observe(this, listResource -> {
      if (listResource != null) {
        if (listResource.data != null) {
          MovieListFragment movieListFragment = null;
          switch (listResource.status) {
            case LOADING:
              displayLoading();
              break;
            case ERROR:
              displayError(listResource.message);
              movieListFragment = MovieListFragment
                  .getInstance(new ArrayList<>(listResource.data), Constants.MOVIES_FROM_SEARCH);
              break;
            case SUCCESS:
              displayMovies();
              movieListFragment = MovieListFragment
                  .getInstance(new ArrayList<>(listResource.data), Constants.MOVIES_FROM_SEARCH);
              break;
          }
          loadFragment(movieListFragment, SEARCH_FRAGMENT_TAG);
        }
      }
    });
    movieListViewModel.getMoviesByType().observe(this, (Resource<List<Movie>> listResource) -> {
      if (listResource != null) {

        if (listResource.data != null) {
          MovieListFragment movieListFragment = null;
          switch (listResource.status) {
            case LOADING:
              displayLoading();
              break;
            case ERROR:
              displayError(listResource.message);
              movieListFragment = MovieListFragment
                  .getInstance(new ArrayList<>(listResource.data), Constants.MOVIES_BY_TYPE);

              break;
            case SUCCESS:
              displayMovies();
              movieListFragment = MovieListFragment
                  .getInstance(new ArrayList<>(listResource.data), Constants.MOVIES_BY_TYPE);
              break;
          }
          loadFragment(movieListFragment, null);
        }
      }
    });
  }


  private void displayLoading() {
    //Method from BaseActivity.java
    showProgressBar(true);
    movieListContainer.setVisibility(View.GONE);
  }

  private void displayError(String message) {
    //Method from BaseActivity.java
    showProgressBar(false);
    movieListContainer.setVisibility(View.VISIBLE);
    toastMessage(message);
  }

  private void displayMovies() {
    //Method from BaseActivity.java
    showProgressBar(false);
    movieListContainer.setVisibility(View.VISIBLE);
  }

  private void toastMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }


  private void initFab() {
    predictionFab.setOnClickListener(view -> {
      Dialog dialog = new Dialog(this);
      Window window = dialog.getWindow();
      if (window != null) {
        //Set transparent background
        window.setBackgroundDrawableResource(android.R.color.transparent);
      }
      dialog.setContentView(R.layout.dialog_chooser);

      ImageView cameraView = dialog.findViewById(R.id.dialog_camera_image_view);
      ImageView galleryView = dialog.findViewById(R.id.dialog_gallery_image_view);
      cameraView.setOnClickListener(view1 -> {
        // Check for the external storage permission
        if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

          // If you do not have permission, request it
          ActivityCompat.requestPermissions(this,
              new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
              REQUEST_STORAGE_PERMISSION);
        } else {
          // Launch the camera if the permission exists
          launchCamera();
        }
      });
      galleryView.setOnClickListener(view12 -> launchGallery());
      dialog.show();
    });
  }


  private void getMovieListByTypeApi(String type) {
    movieListViewModel.getMovieListByTypeApi(type, 1);
  }

  private void getMovieListFromSearch(String query) {
    movieListViewModel.getMovieListFromSearchApi(query, 1);
  }


  private void launchGallery() {
    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    intent.setType(Constants.GALLERY_INTENT_TYPE);
    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
    startActivityForResult(
        Intent.createChooser(intent, getString(R.string.launch_gallery_intent_title)),
        REQUEST_PHOTO_PICKER);

  }

  private void launchCamera() {
    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    if (cameraIntent.resolveActivity(getPackageManager()) != null) {
      // Create the File where the photo should go
      File photoFile = null;
      try {
        photoFile = BitmapUtils.createImageFile(this);


      } catch (IOException ex) {
        // Error occurred while creating the File
        ex.printStackTrace();
      }
      // Continue only if the File was successfully created
      if (photoFile != null) {
        cameraImagePath = photoFile.getAbsolutePath();
        Uri photoURI = FileProvider.getUriForFile(this,
            FILE_PROVIDER_AUTHORITY,
            photoFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
      }

    }
  }


  @Override
  public void onBackStackChanged() {

  }
}








