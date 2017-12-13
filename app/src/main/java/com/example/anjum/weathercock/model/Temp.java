package com.example.anjum.weathercock.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sanjum on 12/13/2017.
 */

public class Temp {
    @SerializedName("day")
    @Expose
    public Float day;
    @SerializedName("min")
    @Expose
    public Float min;
    @SerializedName("max")
    @Expose
    public Float max;
    @SerializedName("night")
    @Expose
    public Float night;
    @SerializedName("eve")
    @Expose
    public Float eve;
    @SerializedName("morn")
    @Expose
    public Float morn;
}
