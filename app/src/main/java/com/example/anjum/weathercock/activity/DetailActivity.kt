package com.example.anjum.weathercock.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.anjum.weathercock.R
import com.example.anjum.weathercock.adapter.HourleyListAdapter
import com.example.anjum.weathercock.adapter.WeeklyListAdapter
import com.example.anjum.weathercock.model.DailyDataModel
import com.example.anjum.weathercock.model.DetailModel
import com.example.anjum.weathercock.model.HourleyModel
import com.example.anjum.weathercock.model.ResultHourleyModel
import com.example.anjum.weathercock.network.ApiClient
import com.example.anjum.weathercock.network.ApiInterface
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_actvity.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : Fitoor() {
    var imageUrl: String = "http://openweathermap.org/img/w/"
    lateinit var progressDialogue: ProgressDialog
    lateinit var itemDailyList: ArrayList<DetailModel>
    lateinit var adapter: WeeklyListAdapter
    lateinit var hourleyadapter: HourleyListAdapter
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
        toolbar1.setNavigationOnClickListener { finish() }
        var detailModel: DetailModel = intent.getSerializableExtra("LIST") as DetailModel
        networkHitforDailyUpdate(detailModel.placename!!)
        networkHitforHourleyUpdate(detailModel.placename!!)
        updateUI(detailModel)
        adapter = WeeklyListAdapter(ArrayList<DetailModel>())
        rv_detail.adapter = adapter
    }

    private fun networkHitforHourleyUpdate(placename: String) {
         progressDialogue.show()
        val apiService=ApiClient.getWeather().create(ApiInterface::class.java)
        val call=apiService.getHourleyResultByName(placename,appid)
        call.enqueue(object : Callback<ResultHourleyModel>{
            override fun onFailure(call: Call<ResultHourleyModel>?, t: Throwable?) {
                progressDialogue.dismiss()
                toast(t?.message.toString())
            }

            override fun onResponse(call: Call<ResultHourleyModel>?, response: Response<ResultHourleyModel>?) {
                if (response!!.isSuccessful){
                    val main_object=response.body()
                    (0..39).forEach{ i ->

                        var hourleyModel=HourleyModel(main_object!!.list[i].main.temp,main_object.list[i].wind.speed,
                                main_object.list[i].main.humidity,main_object.list[i].weather[0].icon,main_object.list[i].dt)
                    }
                    adapter.replaceAll(itemDailyList)
                    progressDialogue.dismiss()



                }

            }


        })

    }

    private fun networkHitforDailyUpdate(placename: String) {
        progressDialogue.show()
        val apiService = ApiClient.getWeather().create(ApiInterface::class.java)
        val call = apiService.getDailyResultByName(placename, appid)
        call.enqueue(object : Callback<DailyDataModel> {
            override fun onFailure(call: Call<DailyDataModel>?, t: Throwable?) {
                progressDialogue.dismiss()
                toast(t?.message.toString())
            }

            override fun onResponse(call: Call<DailyDataModel>?, response: Response<DailyDataModel>?) {
                if (response!!.isSuccessful) {
                    val main_object = response.body()
                    (0..6).forEach { i ->
                        var detailModel: DetailModel = DetailModel(main_object!!.city.country, placename
                                , main_object.list[i].temp.day, main_object.list[i].temp.max, main_object.list[i].temp.min,
                                null, main_object.list[i].humidity, main_object.list[i].pressure, main_object.list[i].weather[0].description,
                                main_object.list[i].dt, null, main_object.list[i].weather[0].icon)
                        itemDailyList.add(detailModel)
                    }

                    adapter.replaceAll(itemDailyList)
                    progressDialogue.dismiss()

                }

            }


        })
    }


    private fun updateUI(model: DetailModel) {
        var imageId: String = model.icon!!
        imageUrl = imageUrl + imageId + png
        tv_detail_temp.text = model.temp!!.toInt().toString() + ceciliusUnicode
        tv_detail_pressure.text = model.pressure!!.toInt().toString() + pressureUnit
        tv_detail_humidity.text = model.humidity.toString() + percent
        tv_detail_description.text = model.description
        tv_detail_place.text = model.placename + "," + model.country
        tv_detail_wind_speed.text = model.windSpeed.toString() + windSpeed
        Picasso.with(this).load(imageUrl).into(iv_detail_image)
        tv_detail_sunRise.text = convertUtcToDate(model.sunrise)
        tv_detail_sunSet.text = convertUtcToDate(model.sunset)


    }
}
