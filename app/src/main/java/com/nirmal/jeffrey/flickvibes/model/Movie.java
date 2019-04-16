package com.nirmal.jeffrey.flickvibes.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movie_table")
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
  @PrimaryKey
  private int id;
  @SerializedName("vote_average")
  @Expose
  @ColumnInfo(name = "vote_average")
  private Double voteAverage;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("poster_path")
  @Expose
  @ColumnInfo(name = "poster_path")
  private String posterPath;
  @SerializedName("backdrop_path")
  @Expose
  @ColumnInfo(name = "backdrop_path")
  private String backdropPath;
  @SerializedName("overview")
  @Expose
  private String overview;
  @SerializedName("release_date")
  @Expose
  @ColumnInfo(name = "release_date")
  private String releaseDate;
  @SerializedName("popularity")
  @Expose
  @ColumnInfo(name = "popularity")
  private Double popularity;
  @ColumnInfo(name = "movie_list_type")
  private String movieListType;
  @ColumnInfo(name = "is_favorite")
  private boolean isFavorite;


  public Movie(int id, Double voteAverage, String title, String posterPath,
      String backdropPath, String overview, String releaseDate, Double popularity,
      String movieListType, boolean isFavorite) {
    this.id = id;
    this.voteAverage = voteAverage;
    this.title = title;
    this.posterPath = posterPath;
    this.backdropPath = backdropPath;
    this.overview = overview;
    this.releaseDate = releaseDate;
    this.popularity = popularity;
    this.movieListType = movieListType;
    this.isFavorite = isFavorite;
  }

  @Ignore
  public Movie(int id, Double voteAverage, String title, String posterPath,
      String backdropPath, String overview, String releaseDate, Double popularity) {
    this.id = id;
    this.voteAverage = voteAverage;
    this.title = title;
    this.posterPath = posterPath;
    this.backdropPath = backdropPath;
    this.overview = overview;
    this.releaseDate = releaseDate;
    this.popularity = popularity;
  }


  @Ignore
  public Movie() {
  }

  protected Movie(Parcel in) {
    id = in.readInt();
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
    if (in.readByte() == 0) {
      popularity = null;
    } else {
      popularity = in.readDouble();
    }
    movieListType = in.readString();
    isFavorite = in.readByte() != 0;
  }

  public boolean isFavorite() {
    return isFavorite;
  }

  public void setFavorite(boolean favorite) {
    isFavorite = favorite;
  }

  public Double getPopularity() {
    return popularity;
  }

  public void setPopularity(Double popularity) {
    this.popularity = popularity;
  }

  public String getMovieListType() {
    return movieListType;
  }

  public void setMovieListType(String movieListType) {
    this.movieListType = movieListType;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
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
    parcel.writeInt(id);
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
    if (popularity == null) {
      parcel.writeByte((byte) 0);
    } else {
      parcel.writeByte((byte) 1);
      parcel.writeDouble(popularity);
    }
    parcel.writeString(movieListType);
    parcel.writeByte((byte) (isFavorite ? 1 : 0));
  }
}


