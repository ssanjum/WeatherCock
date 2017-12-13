package com.example.anjum.weathercock.network;

import com.example.anjum.weathercock.model.ActionResult;
import com.example.anjum.weathercock.model.DailyDataModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hrawat on 9/28/2017.
 */
public interface ApiInterface {

    @GET("/data/2.5/weather")
    Call<ActionResult>

    getResultByLocation(@Query("q") String place,
                        @Query("appid") String appid);

    @GET("/data/2.5/weather")
    Call<ActionResult>

    getResultByLatLong(@Query("lat") Double lat,
                       @Query("lon") Double lon,
                       @Query("appid") String appid);

    @GET("/data/2.5/forecast/daily")
    Call<DailyDataModel>

    getDailyResultByName(@Query("q") String place,
                         @Query("appid") String appid);


}

