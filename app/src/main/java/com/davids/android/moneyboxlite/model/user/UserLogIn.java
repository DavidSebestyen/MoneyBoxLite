package com.davids.android.moneyboxlite.model.user;

import com.google.gson.annotations.SerializedName;

public class UserLogIn {


    @SerializedName("Email")
    String email;

    @SerializedName("Password")
    String password;

    @SerializedName("Idfa")
    String idfa;

    public UserLogIn(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }
}
