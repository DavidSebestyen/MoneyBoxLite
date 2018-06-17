package com.davids.android.moneyboxlite.model.product;

import com.davids.android.moneyboxlite.model.product.Product;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Products {
    @SerializedName("Products")
    List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
