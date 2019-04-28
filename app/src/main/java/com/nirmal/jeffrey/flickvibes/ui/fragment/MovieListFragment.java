package com.nirmal.jeffrey.flickvibes.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import java.util.ArrayList;

public class MovieListFragment extends Fragment implements OnMovieItemClickLister {

  @BindView(R.id.movies_recycler_view)
  RecyclerView recyclerView;
  private Unbinder unbinder;
  private MovieAdapter movieAdapter;
public static MovieListFragment getInstance(ArrayList<Movie> arrayList){
  MovieListFragment fragment = new MovieListFragment();
  Bundle bundle = new Bundle();
  bundle.putParcelableArrayList(Constants.MOVIE_LIST_FRAGMENT_BUNDLE,arrayList);
  fragment.setArguments(bundle);
  return fragment;
}
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_list_movie,container,false);
   unbinder= ButterKnife.bind(this,view);
    initRecyclerView();
    if(getArguments()!=null){
      ArrayList<Movie> movieArrayList =getArguments().getParcelableArrayList(Constants.MOVIE_LIST_FRAGMENT_BUNDLE);
      movieAdapter.setMovieData(movieArrayList);
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
        .error(R.drawable.poster_place_holder)
        .fallback(R.drawable.poster_place_holder);
    return Glide.with(this)
        .setDefaultRequestOptions(requestOptions);

  }
  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void onClickItem(Movie movie) {
    Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
    intent.putExtra(Constants.MOVIE_LIST_INTENT, movie);
    startActivity(intent);
  }

}
