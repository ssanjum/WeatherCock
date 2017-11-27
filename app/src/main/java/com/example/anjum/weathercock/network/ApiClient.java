package com.example.anjum.weathercock.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sanjum on 11/23/2017.
 */
public class ApiClient {

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

    private static Retrofit retrofit = null;

    public static Retrofit getWeather(String base) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(base)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

   /* public static Retrofit getPlacesClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(PLACE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }*/
}
