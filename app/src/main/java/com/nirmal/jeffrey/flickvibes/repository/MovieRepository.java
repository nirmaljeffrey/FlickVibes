package com.nirmal.jeffrey.flickvibes.repository;

import android.content.Context;

public class MovieRepository {
  private static MovieRepository instance;

  private MovieRepository(Context context){


  }
  public static MovieRepository getInstance(Context context){
    if(instance==null){
      instance=new MovieRepository(context);
    }
    return instance;
  }



}
