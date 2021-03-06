package com.nirmal.jeffrey.flickvibes.model;

import static androidx.room.ForeignKey.CASCADE;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "trailer_table", foreignKeys = @ForeignKey(parentColumns = "id",
    childColumns = "movie_id", entity = Movie.class, onDelete = CASCADE), indices = {
    @Index("movie_id")})
public class Trailer implements Parcelable {


  public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
    @Override
    public Trailer createFromParcel(Parcel in) {
      return new Trailer(in);
    }

    @Override
    public Trailer[] newArray(int size) {
      return new Trailer[size];
    }
  };
  @ColumnInfo(name = "movie_id")
  private int movieId;
  @SerializedName("id")
  @Expose
  @ColumnInfo(name = "trailer_id")
  @PrimaryKey
  @NonNull
  private String trailerId;
  @SerializedName("key")
  @Expose
  private String key;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("site")
  @Expose
  private String site;


  public Trailer(int movieId, String key, String name, String site) {
    this.movieId = movieId;
    this.key = key;
    this.name = name;
    this.site = site;
  }


  @Ignore
  public Trailer(String key, String name, String site) {
    this.key = key;
    this.name = name;
    this.site = site;
  }

  protected Trailer(Parcel in) {
    movieId = in.readInt();
    trailerId = in.readString();
    key = in.readString();
    name = in.readString();
    site = in.readString();
  }

  @NonNull
  public String getTrailerId() {
    return trailerId;
  }

  public void setTrailerId(@NonNull String trailerId) {
    this.trailerId = trailerId;
  }

  public int getMovieId() {
    return movieId;
  }

  public void setMovieId(int movieId) {
    this.movieId = movieId;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(movieId);
    parcel.writeString(trailerId);
    parcel.writeString(key);
    parcel.writeString(name);
    parcel.writeString(site);
  }
}
