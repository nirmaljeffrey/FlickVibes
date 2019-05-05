package com.nirmal.jeffrey.flickvibes.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.adapter.MovieAdapter;
import com.nirmal.jeffrey.flickvibes.adapter.MovieAdapter.OnMovieItemClickLister;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.ui.activity.MovieDetailActivity;
import com.nirmal.jeffrey.flickvibes.util.Constants;
import com.nirmal.jeffrey.flickvibes.viewmodel.MovieListViewModel;
import java.util.ArrayList;

public class MovieListFragment extends Fragment implements OnMovieItemClickLister {

  private static final String MOVIE_LIST_FRAGMENT_BUNDLE = "movie_list_fragment_bundle";
  private static final String EMPTY_MOVIE_FRAGMENT_BUNDLE = "empty_movie_bundle";
  @BindView(R.id.movies_recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.empty_layout_image_view)
  ImageView emptyLayoutImageView;
  @BindView(R.id.empty_layout_text_view)
  TextView emptyLayoutTextView;
  @BindView(R.id.movie_list_empty_layout)
  ConstraintLayout emptyLayout;
  private Unbinder unbinder;
  private MovieAdapter movieAdapter;
  private Integer moviePositionInRecyclerView;
  private Integer selectedMovieId;
  private MovieListViewModel fragmentMovieListViewModel;
  public static MovieListFragment getInstance(ArrayList<Movie> arrayList,
      String listTypeIdentifier) {
    MovieListFragment fragment = new MovieListFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelableArrayList(MOVIE_LIST_FRAGMENT_BUNDLE, arrayList);
    bundle.putString(EMPTY_MOVIE_FRAGMENT_BUNDLE, listTypeIdentifier);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if(getActivity()!=null) {
      fragmentMovieListViewModel = ViewModelProviders.of(getActivity())
          .get(MovieListViewModel.class);
    }
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_list_movie, container, false);
    unbinder = ButterKnife.bind(this, view);
    initRecyclerView();
    if (getArguments() != null) {
      String emptyListTag = getArguments().getString(EMPTY_MOVIE_FRAGMENT_BUNDLE);
      ArrayList<Movie> movieArrayList = getArguments()
          .getParcelableArrayList(MOVIE_LIST_FRAGMENT_BUNDLE);
      if (emptyListTag != null) {
        switch (emptyListTag) {
          case Constants.MOVIES_BY_TYPE:
            displayMovieData();
            movieAdapter.setMovieData(movieArrayList);
            break;
          case Constants.MOVIES_FROM_FAVORITES:
            if (movieArrayList == null || movieArrayList.isEmpty()) {
              displayEmptyScreen();
              emptyLayoutImageView.setImageResource(R.drawable.ic_no_favorite_movie);
              emptyLayoutTextView.setText(R.string.fragment_empty_favorites);
            } else {
              displayMovieData();
              movieAdapter.setMovieData(movieArrayList);
            }
            break;
          case Constants.MOVIES_FROM_SEARCH:
            if (movieArrayList == null || movieArrayList.isEmpty()) {
              displayEmptyScreen();
              emptyLayoutImageView.setImageResource(R.drawable.ic_no_results_found);
              emptyLayoutTextView.setText(R.string.fragment_empty_search);
            } else {
              displayMovieData();
              movieAdapter.setMovieData(movieArrayList);
            }
            break;
        }
      }
    }
    return view;
  }

  private void initRecyclerView() {
    int spanCount = getResources().getInteger(R.integer.grid_span_count);
    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));
    movieAdapter = new MovieAdapter(initGlide(), this);
    recyclerView.setAdapter(movieAdapter);
  }

  /**
   * Method for creating request manager for recycler view
   *
   * @return RequestManager (Glide) to be used in recycler view adapter
   */
  private RequestManager initGlide() {
    RequestOptions requestOptions = new RequestOptions()
        .error(R.drawable.ic_poster_place_holder)
        .fallback(R.drawable.ic_poster_place_holder);
    return Glide.with(this)
        .setDefaultRequestOptions(requestOptions);

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void onResume() {
    super.onResume();
    if (movieAdapter.getMovieArrayList() != null) {
      if (moviePositionInRecyclerView != null && selectedMovieId != null) {
        LiveData<Movie> movieLiveData =fragmentMovieListViewModel.getMovie(selectedMovieId);
          movieLiveData.observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
              movieLiveData.removeObserver(this);
              movieAdapter.getMovieArrayList().set(moviePositionInRecyclerView, movie);
              movieAdapter.notifyItemChanged(moviePositionInRecyclerView);
            }
          });

      }
    }
  }

        private void displayEmptyScreen () {
          recyclerView.setVisibility(View.GONE);
          emptyLayout.setVisibility(View.VISIBLE);
        }

        private void displayMovieData () {
          recyclerView.setVisibility(View.VISIBLE);
          emptyLayout.setVisibility(View.GONE);
        }

        @Override
        public void onClickItem ( int position){
          if (movieAdapter.getMovieArrayList() != null) {
            Movie movie = movieAdapter.getMovieArrayList().get(position);
            moviePositionInRecyclerView = position;
            selectedMovieId = movie.getId();
            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra(Constants.MOVIE_LIST_INTENT, movie);
            //Add activity options for activity transitions
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());

          }
        }


}





