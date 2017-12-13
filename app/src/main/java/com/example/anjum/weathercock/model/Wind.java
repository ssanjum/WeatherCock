package com.example.anjum.weathercock.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sanjum on 11/23/2017.
 */

public class Wind {
    @SerializedName("speed")
    @Expose
    public Float speed;
    @SerializedName("deg")
    @Expose
    public Double deg;
}
