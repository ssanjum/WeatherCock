package com.example.anjum.weathercock.activity

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.anjum.weathercock.R
import com.example.anjum.weathercock.adapter.WeeklyListAdapter
import com.example.anjum.weathercock.helper.WeatherHelper
import com.example.anjum.weathercock.model.DailyDataModel
import com.example.anjum.weathercock.model.DetailModel
import com.example.anjum.weathercock.network.ApiClient
import com.example.anjum.weathercock.network.ApiInterface
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_location.*
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
    lateinit var itemDailyList: ArrayList<DetailModel>
    lateinit var adapter: WeeklyListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_actvity)
        setSupportActionBar(toolbar1)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        progressDialogue = ProgressDialog(this)
        progressDialogue.setMessage("Fetching Data...")
        progressDialogue.show()
        rv_detail.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        itemDailyList = java.util.ArrayList()
        weatherHelper = WeatherHelper()
        toolbar1.setNavigationOnClickListener { finish() }
        var detailModel: DetailModel = intent.getSerializableExtra("LIST") as DetailModel
        networkHitforDailyUpdate(detailModel.placename!!)
        updateUI(detailModel)
        adapter = WeeklyListAdapter(ArrayList<DetailModel>())
        rv_detail.adapter = adapter
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
                    for (i in 0..6) {
                        var detailModel: DetailModel = DetailModel(main_object!!.city.country, placename
                                , main_object.list[i].temp.day, main_object.list[i].temp.max, main_object.list[i].temp.min,
                                null, main_object.list[i].humidity, main_object.list[i].pressure, main_object.list[i].weather[0].description,
                                main_object.list[i].dt, null, main_object.list[i].weather[0].icon)
                        itemDailyList.add(detailModel)
                    }

                        adapter.replaceAll(itemDailyList)

                }

            }


        })
    }


    fun updateUI(model: DetailModel) {
        var imageId: String = model.icon!!
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
        var time = weatherHelper.convertUtcToDate(model.sunrise!!)
        tv_detail_sunRise.text = time
        progressDialogue.dismiss()


    }
}
