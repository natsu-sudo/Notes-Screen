package com.coding.aisleassignment.pojo;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
