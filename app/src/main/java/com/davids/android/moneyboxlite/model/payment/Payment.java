package com.davids.android.moneyboxlite.model.payment;

import com.google.gson.annotations.SerializedName;

public class Payment {

    @SerializedName("Amount")
    double amount;

    @SerializedName("InvestorProductId")
    int id;

    public Payment(double amount, int id) {
        this.amount = amount;
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
