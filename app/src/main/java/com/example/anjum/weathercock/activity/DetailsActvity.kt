package com.example.anjum.weathercock.activity

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.anjum.weathercock.R
import com.example.anjum.weathercock.helper.WeatherHelper
import com.example.anjum.weathercock.model.DetailModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_actvity.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.toast

class DetailsActvity : AppCompatActivity() {
    var imageUrl: String = "http://openweathermap.org/img/w/"
    lateinit var progressDialogue: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_actvity)
        setSupportActionBar(toolbar1)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        progressDialogue = ProgressDialog(this)
        progressDialogue.setMessage("Fetching Data...")
        progressDialogue.show()
        toolbar1.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })
        var detailModel: DetailModel = intent.getSerializableExtra("LIST") as DetailModel
        updateUI(detailModel)
    }


    fun updateUI(model: DetailModel) {
        var imageId: String = model.icon
        imageUrl = imageUrl + imageId + ".png"
        tv_detail_temp.text = model.temp.toString() + " \u2103"
        // tv_detail_temp__max.text = model.tempMax.toString() + " \u2103"
        //  tv_detail_temp__min.text = model.tempMin.toString() + " \u2103"
        tv_detail_pressure.text = model.pressure.toString() + " hPa"
        tv_detail_humidity.text = model.humidity.toString() + "%"
        tv_detail_description.text = model.description
        tv_detail_place.text = model.placename + "," + model.country
        tv_detail_wind_speed.text = model.windSpeed.toString() + " mps"
        //Glide.with(this).load(imageUrl).into(iv_detail_image)
        Picasso.with(this).load(imageUrl).into(iv_detail_image)
        var weather:WeatherHelper= WeatherHelper()
        var time=weather.convertUtcToDate(model.sunrise)
        toast(time)
        progressDialogue.dismiss()



    }
}
