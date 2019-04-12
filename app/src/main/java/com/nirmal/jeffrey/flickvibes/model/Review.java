package com.nirmal.jeffrey.flickvibes.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "review_table",foreignKeys = @ForeignKey(entity = Movie.class,
                                   parentColumns = "id",
                                   childColumns = "movie_id",
                                   onDelete = CASCADE))

public class Review implements Parcelable {

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

  @ColumnInfo(name = "room_id")
  @PrimaryKey(autoGenerate = true)
  private int roomId;

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

@Ignore
  public Review(String reviewAuthor, String reviewContent) {
    this.reviewAuthor = reviewAuthor;
    this.reviewContent = reviewContent;
  }

  public Review(String reviewAuthor, String reviewContent, int movieId) {
    this.reviewAuthor = reviewAuthor;
    this.reviewContent = reviewContent;
    this.movieId = movieId;
  }

  protected Review(Parcel in) {
    reviewAuthor = in.readString();
    reviewContent = in.readString();
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
    parcel.writeString(reviewAuthor);
    parcel.writeString(reviewContent);
  }
}
