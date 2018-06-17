package com.davids.android.moneyboxlite.model.payment;

import com.google.gson.annotations.SerializedName;

public class PaymentResponse {
    @SerializedName("MoneyBox")
    double moneybox;

    public PaymentResponse(double moneybox) {
        this.moneybox = moneybox;
    }

    public double getMoneybox() {
        return moneybox;
    }

    public void setMoneybox(double moneybox) {
        this.moneybox = moneybox;
    }
}
