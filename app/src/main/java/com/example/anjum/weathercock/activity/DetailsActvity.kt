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
        progressDialogue = ProgressDialog(this)
        progressDialogue.setMessage("Fetching Data...")
        progressDialogue.show()
        var detailModel: DetailModel = intent.getSerializableExtra("LIST") as DetailModel
        updateUI(detailModel)
    }


    fun updateUI(model: DetailModel) {
        tv_detail_temp.text = model.temp.toString() + " \u2103"
        // tv_detail_temp__max.text = model.tempMax.toString() + " \u2103"
        //  tv_detail_temp__min.text = model.tempMin.toString() + " \u2103"
        tv_detail_pressure.text = model.pressure.toString() + "hPa"
        tv_detail_humidity.text = model.humidity.toString() + "%"
        tv_detail_description.text = model.description
        tv_detail_place.text = model.placename + "," + model.country
        tv_detail_wind_speed.text = model.windSpeed.toString()
        progressDialogue.dismiss()

    }
}
