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
  @ColumnInfo(name = "is_favorite")
  private boolean isFavorite;
  @ColumnInfo(name = "is_popular")
  private boolean isPopular;
  @ColumnInfo(name = "is_top_rated")
  private boolean isTopRated;
  @ColumnInfo(name = "is_upcoming")
  private boolean isUpcoming;
  @ColumnInfo(name = "is_now_playing")
  private boolean isNowPlaying;
  @ColumnInfo(name = "genre")
  private int genre;

  public Movie(Double voteAverage, String title, String posterPath, String backdropPath,
      String overview, String releaseDate, Double popularity, boolean isFavorite, boolean isPopular,
      boolean isTopRated, boolean isUpcoming, boolean isNowPlaying, int genre) {
    this.voteAverage = voteAverage;
    this.title = title;
    this.posterPath = posterPath;
    this.backdropPath = backdropPath;
    this.overview = overview;
    this.releaseDate = releaseDate;
    this.popularity = popularity;
    this.isFavorite = isFavorite;
    this.isPopular = isPopular;
    this.isTopRated = isTopRated;
    this.isUpcoming = isUpcoming;
    this.isNowPlaying = isNowPlaying;
    this.genre = genre;
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
    isFavorite = in.readByte() != 0;
    isPopular = in.readByte() != 0;
    isTopRated = in.readByte() != 0;
    isUpcoming = in.readByte() != 0;
    isNowPlaying = in.readByte() != 0;
    genre = in.readInt();
  }

  public int getGenre() {
    return genre;
  }

  public void setGenre(int genre) {
    this.genre = genre;
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


  public boolean isPopular() {
    return isPopular;
  }

  public void setPopular(boolean popular) {
    isPopular = popular;
  }

  public boolean isTopRated() {
    return isTopRated;
  }

  public void setTopRated(boolean topRated) {
    isTopRated = topRated;
  }

  public boolean isUpcoming() {
    return isUpcoming;
  }

  public void setUpcoming(boolean upcoming) {
    isUpcoming = upcoming;
  }

  public boolean isNowPlaying() {
    return isNowPlaying;
  }

  public void setNowPlaying(boolean nowPlaying) {
    isNowPlaying = nowPlaying;
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
    parcel.writeByte((byte) (isFavorite ? 1 : 0));
    parcel.writeByte((byte) (isPopular ? 1 : 0));
    parcel.writeByte((byte) (isTopRated ? 1 : 0));
    parcel.writeByte((byte) (isUpcoming ? 1 : 0));
    parcel.writeByte((byte) (isNowPlaying ? 1 : 0));
    parcel.writeInt(genre);
  }
}


