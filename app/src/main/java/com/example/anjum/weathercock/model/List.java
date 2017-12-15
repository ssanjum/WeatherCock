package com.example.anjum.weathercock.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sanjum on 12/13/2017.
 */

public class List {
    @SerializedName("dt")
    @Expose
    public Long dt;
    @SerializedName("temp")
    @Expose
    public Temp temp;
    @SerializedName("pressure")
    @Expose
    public Float pressure;
    @SerializedName("humidity")
    @Expose
    public Integer humidity;
    @SerializedName("weather")
    @Expose
    public java.util.List<Weather> weather = null;
    @SerializedName("speed")
    @Expose
    public Float speed;
    @SerializedName("deg")
    @Expose
    public Integer deg;
    @SerializedName("clouds")
    @Expose
    public Integer clouds;
    @SerializedName("rain")
    @Expose
    public Float rain;
}
