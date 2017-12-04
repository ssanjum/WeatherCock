package com.example.anjum.weathercock.activity

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.anjum.weathercock.ActionResult
import com.example.anjum.weathercock.R
import com.example.anjum.weathercock.model.DetailModel
import com.example.anjum.weathercock.network.ApiClient
import com.example.anjum.weathercock.network.ApiInterface
import kotlinx.android.synthetic.main.activity_details_actvity.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.toast
import retrofit2.Call 
import retrofit2.Callback
import retrofit2.Response

class DetailsActvity : AppCompatActivity() {
    lateinit var progressDialogue: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_actvity)
        setSupportActionBar(toolbar1)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        var intent: Intent = intent
        var place: String = intent.getStringExtra("NAME")
        getWeatherDetails(place)
    }

    private fun getWeatherDetails(place: String) {
        progressDialogue = ProgressDialog(this)
        progressDialogue.show()
        progressDialogue.setMessage("Fetching data ....")
        var appid: String = "26f4c901cba4740410b368d8b16a7f53"
        val apiService = ApiClient.getWeather().create(ApiInterface::class.java)
        val call = apiService.getResultByLocation(place, appid)
        call.enqueue(object : Callback<ActionResult> {
            override fun onFailure(call: Call<ActionResult>?, t: Throwable?) {
                progressDialogue.dismiss()
                toast(t?.message.toString())
            }

            override fun onResponse(call: Call<ActionResult>, response: Response<ActionResult>) {

                if (response.isSuccessful) {
                    val main_object = response.body()
                    var temp: Long = main_object!!.main.temp.toLong()
                    var tempinC = temp - 273
                    var description = main_object.weather[0].description
                    var tempMax: Long = main_object!!.main.tempMax.toLong()
                    var tempMin: Long = main_object!!.main.tempMin.toLong()
                    var tempMaxinC = tempMax - 273
                    var tempMininC = tempMin - 273
                    var model: DetailModel = DetailModel(tempinC, tempMaxinC,
                            tempMininC, main_object.wind.speed, main_object.main.humidity, main_object.main.pressure, description)
                    updateUI(model)
                } else if (response.message() == "Not Found") {
                    toast("Invalid place")
                }
            }
        })
    }

    fun updateUI(model: DetailModel) {
        tv_detail_temp.text = model.temp.toString() + " \u2103"
        tv_detail_temp__max.text = model.tempMax.toString() + " \u2103"
        tv_detail_temp__min.text = model.tempMin.toString() + " \u2103"
        tv_detail_pressure.text = model.pressure.toString() + "hPa"
        tv_detail_humidity.text = model.humidity.toString() + "%"
        tv_detail_description.text = model.description
        progressDialogue.dismiss()

    }
}
