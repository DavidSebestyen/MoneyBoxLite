package com.davids.android.moneyboxlite.model.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductDetails implements Parcelable{


    @SerializedName("Name")
    String name;

    @SerializedName("Type")
    String type;

    @SerializedName("AnnualLimit")
    int annualLimit;

    @SerializedName("DepositLimit")
    int depositLimit;

    @SerializedName("FriendlyName")
    String friendlyName;

    public ProductDetails(String name, String type, int annualLimit, int depositLimit, String friendlyName) {
        this.name = name;
        this.type = type;
        this.annualLimit = annualLimit;
        this.depositLimit = depositLimit;
        this.friendlyName = friendlyName;
    }

    protected ProductDetails(Parcel in) {
        name = in.readString();
        type = in.readString();
        annualLimit = in.readInt();
        depositLimit = in.readInt();
        friendlyName = in.readString();
    }

    public static final Creator<ProductDetails> CREATOR = new Creator<ProductDetails>() {
        @Override
        public ProductDetails createFromParcel(Parcel in) {
            return new ProductDetails(in);
        }

        @Override
        public ProductDetails[] newArray(int size) {
            return new ProductDetails[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAnnualLimit() {
        return annualLimit;
    }

    public void setAnnualLimit(int annualLimit) {
        this.annualLimit = annualLimit;
    }

    public double getDepositLimit() {
        return depositLimit;
    }

    public void setDepositLimit(int depositLimit) {
        this.depositLimit = depositLimit;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type);
        dest.writeInt(annualLimit);
        dest.writeInt(depositLimit);
        dest.writeString(friendlyName);
    }
}
