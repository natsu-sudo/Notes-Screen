package com.coding.aisleassignment.pojo;

import com.google.gson.annotations.SerializedName;

public class Profiles {
    @SerializedName("lat")
    String latitude;
    @SerializedName("lng")
    String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
