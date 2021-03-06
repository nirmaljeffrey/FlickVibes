package com.nirmal.jeffrey.flickvibes.model;


import static androidx.room.ForeignKey.CASCADE;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "cast_table", foreignKeys = @ForeignKey(parentColumns = "id",
    childColumns = "movie_id", entity = Movie.class, onDelete = CASCADE), indices = {
    @Index("movie_id")})
public class Cast implements Parcelable {


  public static final Creator<Cast> CREATOR = new Creator<Cast>() {
    @Override
    public Cast createFromParcel(Parcel in) {
      return new Cast(in);
    }

    @Override
    public Cast[] newArray(int size) {
      return new Cast[size];
    }
  };
  @SerializedName("cast_id")
  @Expose
  @ColumnInfo(name = "cast_id")
  @PrimaryKey
  private int castId;
  @ColumnInfo(name = "movie_id")
  private int movieId;
  @SerializedName("name")
  @Expose
  @ColumnInfo(name = "cast_name")
  private String castName;
  @SerializedName("profile_path")
  @Expose
  @ColumnInfo(name = "cast_profile_path")
  private String castProfilePath;
  @SerializedName("order")
  @Expose
  @ColumnInfo(name = "cast_order")
  private int castOrder;

  public Cast(int movieId, String castName, String castProfilePath, int castOrder) {
    this.movieId = movieId;
    this.castName = castName;
    this.castProfilePath = castProfilePath;
    this.castOrder = castOrder;
  }

  @Ignore
  public Cast(String castName, String castProfilePath, int castOrder) {
    this.castName = castName;
    this.castProfilePath = castProfilePath;
    this.castOrder = castOrder;
  }

  protected Cast(Parcel in) {
    castId = in.readInt();
    movieId = in.readInt();
    castName = in.readString();
    castProfilePath = in.readString();
    castOrder = in.readInt();
  }

  public int getCastOrder() {
    return castOrder;
  }

  public void setCastOrder(int castOrder) {
    this.castOrder = castOrder;
  }

  public int getCastId() {
    return castId;
  }

  public void setCastId(int castId) {
    this.castId = castId;
  }

  public int getMovieId() {
    return movieId;
  }

  public void setMovieId(int movieId) {
    this.movieId = movieId;
  }

  public String getCastName() {
    return castName;
  }

  public void setCastName(String castName) {
    this.castName = castName;
  }

  public String getCastProfilePath() {
    return castProfilePath;
  }

  public void setCastProfilePath(String castProfilePath) {
    this.castProfilePath = castProfilePath;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(castId);
    parcel.writeInt(movieId);
    parcel.writeString(castName);
    parcel.writeString(castProfilePath);
    parcel.writeInt(castOrder);
  }
}
