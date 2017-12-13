package com.example.anjum.weathercock.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sanjum on 11/23/2017.
 */

public class Coord {
    @SerializedName("lon")
    @Expose
    public Float lon;
    @SerializedName("lat")
    @Expose
    public Float lat;
}
