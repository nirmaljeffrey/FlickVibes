package com.nirmal.jeffrey.flickvibes.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.database.MovieDatabase;
import com.nirmal.jeffrey.flickvibes.database.dao.MovieDao;
import com.nirmal.jeffrey.flickvibes.model.Movie;
import com.nirmal.jeffrey.flickvibes.util.Constants;
import com.nirmal.jeffrey.flickvibes.util.NetworkUtils;
import com.nirmal.jeffrey.flickvibes.util.WidgetUtils;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MovieWidgetService extends RemoteViewsService {

  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return new MovieWidgetItemFactory(getApplicationContext(), intent);
  }

  class MovieWidgetItemFactory implements RemoteViewsFactory {


    private Context context;
    private int appWidgetId;
    private ArrayList<Movie> movies = new ArrayList<>();

    MovieWidgetItemFactory(Context context, Intent intent) {
      this.context = context;
      this.appWidgetId = intent
          .getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
      MovieDao movieDao = MovieDatabase.getInstance(context).getMovieDao();
      movies = new ArrayList<>(movieDao.getFavoriteMoviesForWidget());
    }

    @Override
    public void onDestroy() {
      movies.clear();

    }

    @Override
    public int getCount() {
      return movies.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
      Movie movie = movies.get(i);
      RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
          R.layout.movie_widget_item);
      String imageUrl = NetworkUtils
          .buildMovieImageURLString(NetworkUtils.POSTER_BASE_URL, movie.getPosterPath());
      try {
        WidgetUtils.loadImageIntoStackView(context, imageUrl, remoteViews);

      } catch (ExecutionException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      remoteViews
          .setTextViewText(R.id.rating_widget_text_view, movie.getVoteAverage().toString());
      Intent fillIntent = new Intent();
      fillIntent.putExtra(Constants.MOVIE_LIST_INTENT, movie);
      fillIntent.putExtra(Constants.WIDGET_INTENT_IDENTIFIER,Constants.WIDGET_CLASS_NAME);
      remoteViews.setOnClickFillInIntent(R.id.movie_linear_layout_widget, fillIntent);
      return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
      return null;
    }

    @Override
    public int getViewTypeCount() {
      return 1;
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public boolean hasStableIds() {
      return true;
    }
  }
}
