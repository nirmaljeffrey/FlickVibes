package com.nirmal.jeffrey.flickvibes.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.nirmal.jeffrey.flickvibes.model.Movie;

@Database(entities = {Movie.class},version = 1 )
public abstract class MovieDatabase extends RoomDatabase {
  public static final String DATABASE_NAME="movies_db";
  private static MovieDatabase instance;
  public static MovieDatabase getInstance(Context context){
    if(instance==null){
      instance= Room.databaseBuilder(context,MovieDatabase.class,DATABASE_NAME).build();
    }
    return instance;
  }


}
