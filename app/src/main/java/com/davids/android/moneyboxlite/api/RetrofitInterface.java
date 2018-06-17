package com.davids.android.moneyboxlite.api;


import com.davids.android.moneyboxlite.model.payment.Payment;
import com.davids.android.moneyboxlite.model.payment.PaymentResponse;
import com.davids.android.moneyboxlite.model.product.Products;
import com.davids.android.moneyboxlite.model.user.UserLogIn;
import com.davids.android.moneyboxlite.model.user.UserLogInResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitInterface {


  @POST("users/login")
    Call<UserLogInResponse> sendLogIn(@Body UserLogIn userLogIn);

  @POST("users/logout")
    Call<Void> sendLogout(@Header("Authorization") String userkey);

  @GET("investorproduct/thisweek")
    Call<Products> getProducts(@Header("Authorization") String userkey);

  @POST("/oneoffpayments")
    Call<PaymentResponse> postPayment(@Body Payment payment, @Header("Authorization") String userkey);
}
