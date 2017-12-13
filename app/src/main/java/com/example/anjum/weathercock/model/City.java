package com.example.anjum.weathercock.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sanjum on 12/13/2017.
 */

 public class City {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("coord")
    @Expose
    public Coord coord;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("population")
    @Expose
    public Integer population;
}
