package com.davids.android.moneyboxlite.model.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable {

    @SerializedName("InvestorProductId")
    int investorProductId;

    @SerializedName("InvestorProductType")
    String investorProductType;

    @SerializedName("MoneyBox")
    double moneybox;

    @SerializedName("Product")
    ProductDetails product;

    @SerializedName("PlanValue")
    double planValue;

    public Product(int investorProductId, String investorProductType, double moneybox, ProductDetails product, double planValue) {
        this.investorProductId = investorProductId;
        this.investorProductType = investorProductType;
        this.moneybox = moneybox;
        this.product = product;
        this.planValue = planValue;
    }

    protected Product(Parcel in) {
        investorProductId = in.readInt();
        investorProductType = in.readString();
        moneybox = in.readDouble();
        planValue = in.readDouble();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int getInvestorProductId() {
        return investorProductId;
    }

    public void setInvestorProductId(int investorProductId) {
        this.investorProductId = investorProductId;
    }

    public String getInvestorProductType() {
        return investorProductType;
    }

    public void setInvestorProductType(String investorProductType) {
        this.investorProductType = investorProductType;
    }

    public double getMoneybox() {
        return moneybox;
    }

    public void setMoneybox(double moneybox) {
        this.moneybox = moneybox;
    }

    public String getFormattedMoneyBox(){
        return "£" + moneybox;
    }

    public ProductDetails getProduct() {
        return product;
    }

    public void setProduct(ProductDetails product) {
        this.product = product;
    }

    public double getPlanValue() {
        return planValue;
    }

    public void setPlanValue(double planValue) {
        this.planValue = planValue;
    }

    public String getFormattedPlanValue(){
        return "£" + planValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(investorProductId);
        dest.writeString(investorProductType);
        dest.writeDouble(moneybox);
        dest.writeDouble(planValue);
    }
}
