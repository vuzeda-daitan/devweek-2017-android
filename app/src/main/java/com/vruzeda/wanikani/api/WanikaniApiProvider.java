package com.vruzeda.wanikani.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WanikaniApiProvider {

    private static WanikaniApi instance;

    public static WanikaniApi getInstance() {
        if (instance == null) {
            instance = createWanikaniApiInstance();
        }
        return instance;
    }

    private static WanikaniApi createWanikaniApiInstance() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        return new Retrofit.Builder()
                .baseUrl("https://www.wanikani.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(WanikaniApi.class);
    }
}
