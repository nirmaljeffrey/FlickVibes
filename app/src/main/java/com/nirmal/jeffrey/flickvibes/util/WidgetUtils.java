package com.nirmal.jeffrey.flickvibes.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nirmal.jeffrey.flickvibes.R;
import java.util.concurrent.ExecutionException;

public final class WidgetUtils {

  private WidgetUtils() {
  }

  public static void loadImageIntoStackView(Context context, String imageUrl,
      RemoteViews remoteViews)
      throws ExecutionException, InterruptedException {
    RequestOptions options = new RequestOptions()
        .centerCrop()
        .error(R.drawable.poster_place_holder)
        .fallback(R.drawable.poster_place_holder);
    Bitmap bitmap = Glide.with(context)
        .asBitmap()
        .apply(options)
        .load(imageUrl)
        .submit()
        .get();
    remoteViews.setImageViewBitmap(R.id.movie_widget_image_View, bitmap);
  }
}

