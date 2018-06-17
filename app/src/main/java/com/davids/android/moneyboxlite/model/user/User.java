package com.davids.android.moneyboxlite.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("Userid")
    String userId;

    @SerializedName("HasVerifiedEmail")
    boolean hasVerifiedEmail;

    @SerializedName("IsPinSet")
    boolean isPinSet;

    @SerializedName("RegistrationStatus")
    String registrationStatus;

    @SerializedName("DateCreated")
    String dateCreated;

    @SerializedName("MoneyboxRegistrationStatus")
    String moneyBoxRegistrationStatus;

    @SerializedName("Email")
    String email;

    @SerializedName("FirstName")
    String firstName;

    @SerializedName("LastName")
    String lastName;

    String bearerToken;

    public User(String userId, boolean hasVerifiedEmail, boolean isPinSet, String registrationStatus, String dateCreated, String moneyBoxRegistrationStatus, String email, String firstName, String lastName) {
        this.userId = userId;
        this.hasVerifiedEmail = hasVerifiedEmail;
        this.isPinSet = isPinSet;
        this.registrationStatus = registrationStatus;
        this.dateCreated = dateCreated;
        this.moneyBoxRegistrationStatus = moneyBoxRegistrationStatus;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    protected User(Parcel in) {
        userId = in.readString();
        hasVerifiedEmail = in.readByte() != 0;
        isPinSet = in.readByte() != 0;
        registrationStatus = in.readString();
        dateCreated = in.readString();
        moneyBoxRegistrationStatus = in.readString();
        email = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        bearerToken = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isHasVerifiedEmail() {
        return hasVerifiedEmail;
    }

    public void setHasVerifiedEmail(boolean hasVerifiedEmail) {
        this.hasVerifiedEmail = hasVerifiedEmail;
    }

    public boolean isPinSet() {
        return isPinSet;
    }

    public void setPinSet(boolean pinSet) {
        isPinSet = pinSet;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getMoneyBoxRegistrationStatus() {
        return moneyBoxRegistrationStatus;
    }

    public void setMoneyBoxRegistrationStatus(String moneyBoxRegistrationStatus) {
        this.moneyBoxRegistrationStatus = moneyBoxRegistrationStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBearerToken() {
        return "Bearer " + bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeByte((byte) (hasVerifiedEmail ? 1 : 0));
        dest.writeByte((byte) (isPinSet ? 1 : 0));
        dest.writeString(registrationStatus);
        dest.writeString(dateCreated);
        dest.writeString(moneyBoxRegistrationStatus);
        dest.writeString(email);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(bearerToken);
    }
}
