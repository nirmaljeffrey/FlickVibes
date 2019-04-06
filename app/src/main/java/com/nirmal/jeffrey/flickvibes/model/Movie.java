package com.nirmal.jeffrey.flickvibes.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

  public static final Creator<Movie> CREATOR = new Creator<Movie>() {
    @Override
    public Movie createFromParcel(Parcel in) {
      return new Movie(in);
    }

    @Override
    public Movie[] newArray(int size) {
      return new Movie[size];
    }
  };
  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("vote_average")
  @Expose
  private Double voteAverage;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("poster_path")
  @Expose
  private String posterPath;
  @SerializedName("backdrop_path")
  @Expose
  private String backdropPath;
  @SerializedName("overview")
  @Expose
  private String overview;
  @SerializedName("release_date")
  @Expose
  private String releaseDate;

  public Movie(Integer id, Double voteAverage, String title, String posterPath,
      String backdropPath, String overview, String releaseDate) {
    this.id = id;
    this.voteAverage = voteAverage;
    this.title = title;
    this.posterPath = posterPath;
    this.backdropPath = backdropPath;
    this.overview = overview;
    this.releaseDate = releaseDate;
  }

  public Movie() {
  }

  protected Movie(Parcel in) {
    if (in.readByte() == 0) {
      id = null;
    } else {
      id = in.readInt();
    }
    if (in.readByte() == 0) {
      voteAverage = null;
    } else {
      voteAverage = in.readDouble();
    }
    title = in.readString();
    posterPath = in.readString();
    backdropPath = in.readString();
    overview = in.readString();
    releaseDate = in.readString();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Double getVoteAverage() {
    return voteAverage;
  }

  public void setVoteAverage(Double voteAverage) {
    this.voteAverage = voteAverage;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public String getBackdropPath() {
    return backdropPath;
  }

  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    if (id == null) {
      parcel.writeByte((byte) 0);
    } else {
      parcel.writeByte((byte) 1);
      parcel.writeInt(id);
    }
    if (voteAverage == null) {
      parcel.writeByte((byte) 0);
    } else {
      parcel.writeByte((byte) 1);
      parcel.writeDouble(voteAverage);
    }
    parcel.writeString(title);
    parcel.writeString(posterPath);
    parcel.writeString(backdropPath);
    parcel.writeString(overview);
    parcel.writeString(releaseDate);
  }
}


