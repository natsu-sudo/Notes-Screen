package com.coding.aisleassignment.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Invites {
    @SerializedName("profiles")
    ArrayList<Profiles> profiles;

    public ArrayList<Profiles> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Profiles> profiles) {
        this.profiles = profiles;
    }
}
