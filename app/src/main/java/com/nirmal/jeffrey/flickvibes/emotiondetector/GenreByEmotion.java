package com.nirmal.jeffrey.flickvibes.emotiondetector;

import java.util.Random;

public final class GenreByEmotion {

  //Refer the below link to get the integer value of genre
  //https://developers.themoviedb.org/3/genres/get-movie-list
  private static int[] sadGenreArray = {35,//Comedy Genre
      10402,//Music Genre
      10751//Family Genre
  };
  private static int[] happyGenreArray = {14,//Fantasy Genre
      10749,//Romance Genre
      53//Thriller Genre
  };
  private static int[] normalGenreArray = {18,//Drama Genre
      28,//Action Genre
      36//History Genre
  };

  private GenreByEmotion() {

  }

  private static int[] getGenreArrayByEmotion(Emotions emotion) {
    switch (emotion) {
      case HAPPY:
        return happyGenreArray;
      case NORMAL:
        return normalGenreArray;
      case SAD:
        return sadGenreArray;
      default:
        throw new IllegalArgumentException(
            "The genreArray should of three types - happy, sad, normal");
    }
  }

  private static int getGenreFromArray(int[] array) {
    Random random = new Random();
    return array[random.nextInt(array.length)];
  }

  public static int getGenreByEmotion(Emotions emotions) {
    int[] genreArray = getGenreArrayByEmotion(emotions);
    return getGenreFromArray(genreArray);
  }
}
