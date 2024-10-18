package com.example.feedbackmanagementsystem.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static String baseURL = "https://671264d36c5f5ced662356fe.mockapi.io/";
    private static Retrofit retrofit;

    public static Retrofit getClient(){
        if (retrofit == null){
            // converterFactory (de)serialize the object
            // GSON -> JSON from server -> POJO for Java to process, or the other way around.
            retrofit = new Retrofit.Builder().baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }
}
