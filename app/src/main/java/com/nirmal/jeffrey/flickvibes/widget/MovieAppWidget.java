package com.nirmal.jeffrey.flickvibes.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.ui.activity.MovieDetailActivity;

/**
 * Implementation of App Widget functionality.
 */
public class MovieAppWidget extends AppWidgetProvider {

  @SuppressWarnings("WeakerAccess")
  static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
      int appWidgetId) {
    // Construct the RemoteViews object
    Intent serviceIntent = new Intent(context, MovieWidgetService.class);
    serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
    serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

    Intent clickIntent = new Intent(context, MovieDetailActivity.class);
    PendingIntent clickPendingIntent = PendingIntent
        .getActivity(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.movie_app_widget);
    views.setRemoteAdapter(R.id.movie_stack_view_widget, serviceIntent);
    views.setEmptyView(R.id.movie_stack_view_widget, R.id.empty_widget_Text_view);
    views.setPendingIntentTemplate(R.id.movie_stack_view_widget, clickPendingIntent);
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views);
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    // There may be multiple widgets active, so update all of them
    for (int appWidgetId : appWidgetIds) {
      updateAppWidget(context, appWidgetManager, appWidgetId);
    }
  }

  @Override
  public void onEnabled(Context context) {
    // Enter relevant functionality for when the first widget is created
  }

  @Override
  public void onDisabled(Context context) {
    // Enter relevant functionality for when the last widget is disabled
  }
}

