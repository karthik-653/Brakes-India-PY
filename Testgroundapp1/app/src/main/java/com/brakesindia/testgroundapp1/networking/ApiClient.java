package com.brakesindia.testgroundapp1.networking;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    static Retrofit retrofit;

    static UserService userService;
    public ApiClient(String url){
        System.out.println("apiclient start");
        ApiClient.retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient.retrofit=ApiClient.getRetrofit();

        ApiClient.userService = retrofit.create(UserService.class);
        System.out.println("apiclient end");
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static void setRetrofit(Retrofit retrofit) {
        ApiClient.retrofit = retrofit;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static void setUserService(UserService userService) {
        ApiClient.userService = userService;
    }
}
