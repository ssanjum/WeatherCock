package com.example.anjum.weathercock.helper

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by sanjum on 12/11/2017.
 */
class WeatherHelper {

    open fun convertUtcToDate(utc: Long): String {
        //convert seconds to milliseconds
        val date = Date(utc * 1000L)
        // the format of your date
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z")
        // give a timezone reference for formatting (see comment at the bottom)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(date)
    }
}