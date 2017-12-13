package com.example.anjum.weathercock.activity

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.anjum.weathercock.R
import com.example.anjum.weathercock.helper.WeatherHelper
import com.example.anjum.weathercock.model.ActionResult
import com.example.anjum.weathercock.model.DailyDataModel
import com.example.anjum.weathercock.model.DetailModel
import com.example.anjum.weathercock.network.ApiClient
import com.example.anjum.weathercock.network.ApiInterface
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_actvity.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    var imageUrl: String = "http://openweathermap.org/img/w/"
    lateinit var weatherHelper: WeatherHelper
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
        weatherHelper = WeatherHelper()
        toolbar1.setNavigationOnClickListener { finish() }
        var detailModel: DetailModel = intent.getSerializableExtra("LIST") as DetailModel
        networkHitforDailyUpdate(detailModel.placename)
        updateUI(detailModel)
    }

    private fun networkHitforDailyUpdate(placename: String) {
        val apiService = ApiClient.getWeather().create(ApiInterface::class.java)
        val call = apiService.getDailyResultByName(placename, weatherHelper.appid)
        call.enqueue(object : Callback<DailyDataModel> {
            override fun onFailure(call: Call<DailyDataModel>?, t: Throwable?) {
                progressDialogue.dismiss()
                toast(t?.message.toString())
            }

            override fun onResponse(call: Call<DailyDataModel>?, response: Response<DailyDataModel>?) {
                progressDialogue.dismiss()
                if (response!!.isSuccessful) {
                    val main_object = response.body()
                    var jabbu = main_object!!.city.name
                }

            }


        })
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
        Picasso.with(this).load(imageUrl).into(iv_detail_image)
        var time = weatherHelper.convertUtcToDate(model.sunrise)
        tv_detail_sunRise.text = time
        progressDialogue.dismiss()


    }
}
