package com.example.anjum.weathercock.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sanjum on 11/23/2017.
 */

public class Main {

    @SerializedName("temp")
    @Expose
    public Float temp;
    @SerializedName("pressure")
    @Expose
    public Float pressure;
    @SerializedName("humidity")
    @Expose
    public Integer humidity;
    @SerializedName("temp_min")
    @Expose
    public Float tempMin;
    @SerializedName("temp_max")
    @Expose
    public Float tempMax;
    @SerializedName("sea_level")
    @Expose
    public Float seaLevel;
    @SerializedName("grnd_level")
    @Expose
    public Float grndLevel;
}
