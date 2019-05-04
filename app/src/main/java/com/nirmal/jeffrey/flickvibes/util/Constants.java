package com.nirmal.jeffrey.flickvibes.util;

public final class Constants {

  public static final String MOVIE_LIST_INTENT = "movie_parcel";
  public static final String GALLERY_ACTIVITY_INTENT = "gallery_image";
  public static final String CAMERA_ACTIVITY_INTENT = "camera_image";
  public static final String GALLERY_INTENT_TYPE = "image/jpeg";
  public static final String EMOTION_ACTIVITY_INTENT = "emotion_intent";
  public static final String EMOTION_BUNDLE_KEY = "emotion_bundle";
  public static final String EMOTION_ERROR_BUNDLE_KEY = "emotion_error_bundle";
  public static final String EMOTION_ERROR_DIALOG_TAG = "emotion_error_dialog";
  public static final String EMOTION_DIALOG_TAG = "emotion_dialog";
  public static final String EMOTION_MOVIE_LIST_INTENT = "emotion_movie_list_intent";
  public static final String MOVIES_FROM_FAVORITES = "movies_from_favorites";
  public static final String MOVIES_FROM_SEARCH = "movies_from_search";
  public static final String MOVIES_BY_TYPE = "movies_by_type";
  public static final String WIDGET_INTENT_IDENTIFIER="widget_intent";
  public static final String WIDGET_CLASS_NAME="movie_widget";
  //Emotion Detector probabilities
  public static final double HAPPINESS_PROB = 0.4;
  public static final double SADNESS_PROB = 0.2;

  private Constants() {
  }
}
