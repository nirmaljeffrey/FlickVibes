package com.nirmal.jeffrey.flickvibes.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
  @SerializedName("key")
  @Expose
  private String key;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("site")
  @Expose
  private String site;

  public Trailer(String key, String name, String site) {
    this.key = key;
    this.name = name;
    this.site = site;
  }

  protected Trailer(Parcel in) {
    key = in.readString();
    name = in.readString();
    site = in.readString();
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
    parcel.writeString(key);
    parcel.writeString(name);
    parcel.writeString(site);
  }


}
