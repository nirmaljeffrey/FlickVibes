package com.nirmal.jeffrey.flickvibes.model;

import static androidx.room.ForeignKey.CASCADE;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "review_table", foreignKeys = @ForeignKey(parentColumns = "id",
    childColumns = "movie_id", entity = Movie.class, onDelete = CASCADE))

public class Review implements Parcelable {

  @SerializedName("id")
  @Expose
  @ColumnInfo(name = "review_id")
  @PrimaryKey
  @NonNull
  private String reviewId;
  @SerializedName("author")
  @Expose
  @ColumnInfo(name = "review_author")
  private String reviewAuthor;
  @SerializedName("content")
  @Expose
  @ColumnInfo(name = "review_content")
  private String reviewContent;
  @ColumnInfo(name = "movie_id")
  private int movieId;

  public Review(String reviewAuthor, String reviewContent, int movieId) {
    this.reviewAuthor = reviewAuthor;
    this.reviewContent = reviewContent;
    this.movieId = movieId;

  }


  @Ignore
  public Review(String reviewAuthor, String reviewContent) {
    this.reviewAuthor = reviewAuthor;
    this.reviewContent = reviewContent;
  }


  protected Review(Parcel in) {
    reviewId = in.readString();
    reviewAuthor = in.readString();
    reviewContent = in.readString();
    movieId = in.readInt();
  }

  public static final Creator<Review> CREATOR = new Creator<Review>() {
    @Override
    public Review createFromParcel(Parcel in) {
      return new Review(in);
    }

    @Override
    public Review[] newArray(int size) {
      return new Review[size];
    }
  };


  public String getReviewId() {
    return reviewId;
  }

  public void setReviewId(String reviewId) {
    this.reviewId = reviewId;
  }

  public int getMovieId() {
    return movieId;
  }

  public void setMovieId(int movieId) {
    this.movieId = movieId;
  }

  public String getReviewAuthor() {
    return reviewAuthor;
  }

  public void setReviewAuthor(String reviewAuthor) {
    this.reviewAuthor = reviewAuthor;
  }

  public String getReviewContent() {
    return reviewContent;
  }

  public void setReviewContent(String reviewContent) {
    this.reviewContent = reviewContent;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(reviewId);
    parcel.writeString(reviewAuthor);
    parcel.writeString(reviewContent);
    parcel.writeInt(movieId);
  }
}
