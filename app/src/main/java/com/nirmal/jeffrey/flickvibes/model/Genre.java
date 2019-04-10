package com.nirmal.jeffrey.flickvibes.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
  @SerializedName("id")
  @Expose
  private int genreId;
  @SerializedName("name")
  @Expose
  private String genreName;

  public Genre(int genreId, String genreName) {
    this.genreId = genreId;
    this.genreName = genreName;
  }

  protected Genre(Parcel in) {
    genreId = in.readInt();
    genreName = in.readString();
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
    parcel.writeInt(genreId);
    parcel.writeString(genreName);
  }
}
