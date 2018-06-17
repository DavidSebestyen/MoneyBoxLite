package com.davids.android.moneyboxlite.model.user;

import com.google.gson.annotations.SerializedName;

public class UserLogInResponse {


    @SerializedName("User")
    User userDetails;

    @SerializedName("Session")
    Session session;

    public UserLogInResponse(User userDetails, Session session) {
        this.userDetails = userDetails;
        this.session = session;
    }

    public User getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(User userDetails) {
        this.userDetails = userDetails;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
