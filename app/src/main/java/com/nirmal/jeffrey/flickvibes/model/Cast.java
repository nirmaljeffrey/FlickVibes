package com.nirmal.jeffrey.flickvibes.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


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
  @SerializedName("name")
  @Expose
  private String castName;
  @SerializedName("profile_path")
  @Expose
  private String castProfilePath;

  public Cast(String castName, String castProfilePath) {
    this.castName = castName;
    this.castProfilePath = castProfilePath;
  }

  protected Cast(Parcel in) {
    castName = in.readString();
    castProfilePath = in.readString();
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
    parcel.writeString(castName);
    parcel.writeString(castProfilePath);
  }
}
