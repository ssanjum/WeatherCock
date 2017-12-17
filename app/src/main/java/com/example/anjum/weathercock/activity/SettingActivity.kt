package com.example.anjum.weathercock.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.example.anjum.weathercock.R

class SettingActivity : Fitoor() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        var dm= DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        var width:Int=dm.widthPixels
        var height:Int=dm.heightPixels
        window.setLayout((width*.8).toInt(), (height*.8).toInt())
    }
}
