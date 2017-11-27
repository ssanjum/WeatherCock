package com.example.anjum.weathercock

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.anjum.weathercock.network.ApiClient
import com.example.anjum.weathercock.network.ApiInterface
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddLocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)
        setSupportActionBar(toolbar1)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar1.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_location, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_add) {
            showDialog()

        }
        if (item?.itemId == R.id.menu_settings) {
            toast("TO DO")
        }
        return true

    }

    private fun showDialog() {

        var builder: AlertDialog.Builder = AlertDialog.Builder(this)
        var inflater: LayoutInflater = layoutInflater
        var view: View = inflater.inflate(R.layout.add_place_dialog, null)
        var editgetPlace: EditText = view.findViewById(R.id.ed_dialog_get_place)
        var button: Button = view.findViewById(R.id.btn_dialog_add)

        button.setOnClickListener {
            // toast(place)
            var place: String = editgetPlace.text.toString().trim()
            var appid:String="&appid=26f4c901cba4740410b368d8b16a7f53"
            var baseeURL:String=place+appid
            var  mainURL:String= "https://api.openweathermap.org/data/2.5/weather"+"?q="+baseeURL
            val apiService = ApiClient.getWeather(mainURL).create(ApiInterface::class.java)
            val call = apiService.nearByPlaces
            call.enqueue(object : Callback<ActionResult> {
                override fun onFailure(call: Call<ActionResult>?, t: Throwable?) {

                }

                override fun onResponse(call: Call<ActionResult>, response: Response<ActionResult>) {
                    val main_object = response.body()
                    var temp: Long = main_object!!.main.temp.toLong()
                    toast(temp?.toString())
                }
            })
        }
        builder.setView(view)
        builder.show()

    }

}
