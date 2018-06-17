package com.davids.android.moneyboxlite.model.user;

import com.google.gson.annotations.SerializedName;

public class Session {


    @SerializedName("BearerToken")
    String bearerToken;

    public Session(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }

}
