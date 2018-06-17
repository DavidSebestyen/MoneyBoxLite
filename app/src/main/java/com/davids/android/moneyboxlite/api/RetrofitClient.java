package com.davids.android.moneyboxlite.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api-test00.moneyboxapp.com";

    //    AppId	3a97b932a9d449c981b595
//    Content-Type	application/json
//    appVersion	4.11.0
//    apiVersion	3.0.0

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(100, TimeUnit.SECONDS);
            httpClient.readTimeout(100,TimeUnit.SECONDS).build();
            httpClient.addInterceptor(new Interceptor() {
                                          @Override
                                          public Response intercept(Interceptor.Chain chain) throws IOException {
                                              Request original = chain.request();

                                              Request request = original.newBuilder()
                                                      .header("AppId", "3a97b932a9d449c981b595")
                                                      .header("Content-Type", "application/json")
                                                      .header("appVersion", "4.11.0")
                                                      .header("apiVersion", "3.0.0")
                                                      .method(original.method(), original.body())
                                                      .build();

                                              return chain.proceed(request);
                                          }
                                      });


                    OkHttpClient client = httpClient.build();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
