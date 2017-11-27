package com.example.anjum.weathercock.network;

import com.example.anjum.weathercock.ActionResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by hrawat on 9/28/2017.
 */
public interface ApiInterface {

    @POST(".")
    Call<ActionResult> getNearByPlaces();

}

