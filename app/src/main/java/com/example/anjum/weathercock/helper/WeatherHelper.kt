package com.example.anjum.weathercock.helper

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by sanjum on 12/11/2017.
 */
class WeatherHelper {
    val appid: String = "26f4c901cba4740410b368d8b16a7f53"

    fun convertUtcToDate(utc: Long): String {
        //convert seconds to milliseconds
        val date = Date(utc * 1000L)
        // the format of your date
        val sdf = SimpleDateFormat("HH:mm a", Locale.getDefault())
        return sdf.format(date)
    }

    fun convertUtcToDaY(utc: Long): String {
        //convert seconds to milliseconds
        val date = Date(utc * 1000L)
        val sdf = SimpleDateFormat("EE")
        val dayOfWeek = sdf.format(date)
        return dayOfWeek

    }
}