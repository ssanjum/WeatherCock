package com.example.anjum.weathercock.model

import java.io.Serializable

/**
 * Created by ANJUM on 12/3/2017.
 */
data class DetailModel(var country:String?, var placename:String?, var temp: Float?, var tempMax: Float?,
                       var tempMin: Float?, var windSpeed: Float?, var humidity: Int?,
                       var pressure: Float?, var description: String?, var sunset: Long?, var sunrise: Long?, var icon:String?):Serializable