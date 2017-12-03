package com.example.anjum.weathercock.model

/**
 * Created by ANJUM on 12/3/2017.
 */
data class DetailModel(var temp: Long, var tempMax: Long,
                       var tempMin: Long, var windSpeed: Float, var humidity: Int, var pressure: Float,var description:String)