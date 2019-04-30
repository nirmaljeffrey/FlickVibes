package com.nirmal.jeffrey.flickvibes.util;


import static android.os.Environment.getExternalStoragePublicDirectory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BitmapUtils {


  public static File createImageFile(Context context) throws IOException {
    // Create an image file name
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String imageFileName = "JPEG_" + timeStamp + "_";
    File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

    return File.createTempFile(imageFileName,  /* prefix */
        ".jpg",         /* suffix */
        storageDir      /* directory */
    );
  }

  /**
   * Resamples the captured photo to fit the screen for better memory usage.
   *
   * @param context The application context.
   * @param imagePath The path of the photo to be resampled.
   * @return The resampled bitmap
   */
  public static Bitmap resamplePic(Context context, String imagePath) {

    // Get device screen size information
    DisplayMetrics metrics = new DisplayMetrics();
    WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    manager.getDefaultDisplay().getMetrics(metrics);

    int targetH = metrics.heightPixels;
    int targetW = metrics.widthPixels;

    // Get the dimensions of the original bitmap
    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
    bmOptions.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(imagePath, bmOptions);
    int photoW = bmOptions.outWidth;
    int photoH = bmOptions.outHeight;

    // Determine how much to scale down the image
    int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

    // Decode the image file into a Bitmap sized to fill the View
    bmOptions.inJustDecodeBounds = false;
    bmOptions.inSampleSize = scaleFactor;

    return BitmapFactory.decodeFile(imagePath);
  }


  /**
   * Resamples the captured photo to fit the screen for better memory usage.
   *
   * @param context The application context.
   * @param imageUri The Uri of the photo to be resampled.
   * @return The resampled bitmap
   * @throws FileNotFoundException Thrown if the file is not found
   */
  public static Bitmap resamplePic(Context context, Uri imageUri) throws FileNotFoundException {

    // Get device screen size information
    DisplayMetrics metrics = new DisplayMetrics();
    WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    manager.getDefaultDisplay().getMetrics(metrics);

    int targetH = metrics.heightPixels;
    int targetW = metrics.widthPixels;

    // Get the dimensions of the original bitmap
    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
    bmOptions.inJustDecodeBounds = true;

    BitmapFactory
        .decodeStream(context.getContentResolver().openInputStream(imageUri), null, bmOptions);
    int photoW = bmOptions.outWidth;
    int photoH = bmOptions.outHeight;

    // Determine how much to scale down the image
    int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

    // Decode the image file into a Bitmap sized to fill the View
    bmOptions.inJustDecodeBounds = false;
    bmOptions.inSampleSize = scaleFactor;

    return BitmapFactory
        .decodeStream(context.getContentResolver().openInputStream(imageUri), null, bmOptions);
  }


}

