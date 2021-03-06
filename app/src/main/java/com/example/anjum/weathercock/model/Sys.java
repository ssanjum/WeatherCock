package com.example.anjum.weathercock.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sanjum on 11/23/2017.
 */

 public class Sys {

    @SerializedName("message")
    @Expose
    public Float message;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("sunrise")
    @Expose
    public Long sunrise;
    @SerializedName("sunset")
    @Expose
    public Long sunset;
}
