package com.nirmal.jeffrey.flickvibes.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "genre_table")
public class Genre implements Parcelable {

  public static final Creator<Genre> CREATOR = new Creator<Genre>() {
    @Override
    public Genre createFromParcel(Parcel in) {
      return new Genre(in);
    }

    @Override
    public Genre[] newArray(int size) {
      return new Genre[size];
    }
  };
  @ColumnInfo(name = "room_id")
  @PrimaryKey(autoGenerate = true)
  private int roomId;
  @ColumnInfo(name = "movie_id")
  private int movieId;
  @SerializedName("id")
  @Expose
  private int genreId;
  @SerializedName("name")
  @Expose
  private String genreName;

  public Genre(int movieId, int genreId, String genreName) {
    this.movieId = movieId;
    this.genreId = genreId;
    this.genreName = genreName;
  }

  @Ignore
  public Genre(int genreId, String genreName) {
    this.genreId = genreId;
    this.genreName = genreName;
  }

  protected Genre(Parcel in) {
    roomId = in.readInt();
    movieId = in.readInt();
    genreId = in.readInt();
    genreName = in.readString();
  }

  public int getRoomId() {
    return roomId;
  }

  public void setRoomId(int roomId) {
    this.roomId = roomId;
  }

  public int getMovieId() {
    return movieId;
  }

  public void setMovieId(int movieId) {
    this.movieId = movieId;
  }

  public int getGenreId() {
    return genreId;
  }

  public void setGenreId(int genreId) {
    this.genreId = genreId;
  }

  public String getGenreName() {
    return genreName;
  }

  public void setGenreName(String genreName) {
    this.genreName = genreName;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(roomId);
    parcel.writeInt(movieId);
    parcel.writeInt(genreId);
    parcel.writeString(genreName);
  }
}
