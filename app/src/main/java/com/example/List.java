package com.example;

import com.example.anjum.weathercock.model.Main;
import com.example.anjum.weathercock.model.Sys;
import com.example.anjum.weathercock.model.Weather;
import com.example.anjum.weathercock.model.Wind;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sanjum on 12/19/2017.
 */

public class List {
    @SerializedName("dt")
    @Expose
    public Long dt;
    @SerializedName("main")
    @Expose
    public Main main;
    @SerializedName("weather")
    @Expose
    public java.util.List<Weather> weather = null;
    @SerializedName("wind")
    @Expose
    public Wind wind;
    @SerializedName("sys")
    @Expose
    public Sys sys;
    @SerializedName("dt_txt")
    @Expose
    public String dtTxt;

}
