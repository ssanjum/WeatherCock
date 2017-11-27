package com.example.anjum.weathercock.network;

import com.example.anjum.weathercock.ActionResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by hrawat on 9/28/2017.
 */
public interface ApiInterface {

    @GET("/data/2.5/weather")
    Call<ActionResult>

    getNearByPlaces(@Query("q") String place,
                    @Query("appid") String appid);

}

