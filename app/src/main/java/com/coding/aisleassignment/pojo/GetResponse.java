package com.coding.aisleassignment.pojo;

import com.google.gson.annotations.SerializedName;

public class GetResponse {
    @SerializedName("invites")
    Invites invites;

    public Invites getInvites() {
        return invites;
    }

    public void setInvites(Invites invites) {
        this.invites = invites;
    }
}
