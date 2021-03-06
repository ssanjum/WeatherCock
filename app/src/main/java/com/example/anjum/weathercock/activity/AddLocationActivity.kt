package com.example.anjum.weathercock.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SwitchCompat
import android.view.*
import android.widget.*
import com.example.anjum.weathercock.model.ActionResult
import com.example.anjum.weathercock.BuildConfig
import com.example.anjum.weathercock.R
import com.example.anjum.weathercock.adapter.AddLocationAdapter
import com.example.anjum.weathercock.event.AlertEvent
import com.example.anjum.weathercock.model.DetailModel
import com.example.anjum.weathercock.model.WeatherModel
import com.example.anjum.weathercock.network.ApiClient
import com.example.anjum.weathercock.network.ApiInterface
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_add_location.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddLocationActivity : Fitoor(), GoogleApiClient.OnConnectionFailedListener {


    private var apiClient: GoogleApiClient? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    lateinit var adapter: AddLocationAdapter
    var itemList: ArrayList<DetailModel>? = null
    var itemDialoglist: ArrayList<DetailModel>? = null
    lateinit var bnd: Bundle

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var progressDialogue: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)
        setSupportActionBar(toolbar1)
        progressDialogue = ProgressDialog(this)
        progressDialogue.show()
        progressDialogue.setMessage("Fetching data ....")
        Hawk.init(this).build()
        itemDialoglist = ArrayList()
        itemList = ArrayList()
        EventBus.getDefault().register(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar1.setNavigationOnClickListener { finish() }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        rv_add_location.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        itemList = Hawk.get("MyKey")
        if (itemList != null) {
            var model: WeatherModel
            adapter = AddLocationAdapter(itemList!!, this)
            itemList!!.forEach { model ->
                var name = model.placename
                networkHitWithPlace(name!!)
            }
        } else {
            adapter = AddLocationAdapter(ArrayList<DetailModel>(), this)
        }
        rv_add_location.adapter = adapter
    }

    private fun networkHitWithPlace(name: String) {
        val apiService = ApiClient.getWeather().create(ApiInterface::class.java)
        val call = apiService.getResultByLocation(name, appid)
        call.enqueue(object : Callback<ActionResult> {
            override fun onFailure(call: Call<ActionResult>?, t: Throwable?) {
                progressDialogue.dismiss()
                toast(t?.message.toString())
            }

            override fun onResponse(call: Call<ActionResult>?, response: Response<ActionResult>?) {
                if (response!!.isSuccessful) {
                    progressDialogue.dismiss()
                    val main_object = response.body()
                    var temp: Float = main_object!!.main.temp
                    var tempinC = convertToTempratureUnit(temp, "C")
                    var tempMax: Float = main_object!!.main.tempMax
                    var tempMaxC = convertToTempratureUnit(temp, "C")
                    var tempMin: Float = main_object!!.main.tempMin
                    var tempMinC = convertToTempratureUnit(temp, "C")
                    var detailModel: DetailModel = DetailModel(main_object.sys.country, main_object!!.name, tempinC, tempMaxC, tempMinC, main_object.wind.speed, main_object.main.humidity,
                            main_object.main.pressure, main_object.weather[0].description, main_object.sys.sunset, main_object.sys.sunrise, main_object.weather[0].icon)

                    itemDialoglist!!.add(detailModel)
                    adapter.addItem(itemDialoglist)
                    adapter.setOnListClickListener(object : AddLocationAdapter.OnListClickListener {

                        override fun onItemClick(pos: Int) {
                            detailModel = adapter.getModelAtposition(pos)
                            bnd = Bundle()
                            val intent = Intent(baseContext, DetailActivity::class.java)
                            bnd.putSerializable("LIST", detailModel)
                            intent.putExtras(bnd)
                            startActivity(intent)
                        }

                        override fun onLongPress(pos: Int) {
                            if (itemList!!.size > 1) {
                                showDeleteDialog(pos)
                            }

                        }
                    })

                }

            }
        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_location, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_add -> showDialog()
            R.id.menu_settings -> showSetting() //routeActivity(this,SettingActivity::class.java)
        }
        return true
    }

    private fun showSetting() {
        var builder = AlertDialog.Builder(this)
        var view = layoutInflater.inflate(R.layout.activity_setting, null)
        var toggle = view.findViewById<SwitchCompat>(R.id.switchTemperature)
        toggle.setOnCheckedChangeListener { buttonView, isChecked ->
            var event = AlertEvent()
            if (isChecked){
                event.isFarienhate = true
                toggle.text=fahrenheitUnicode
        }
            else{
            event.isCelcius = true
                toggle.text=ceciliusUnicode
        }
            EventBus.getDefault().post(event)
        }
        builder.setView(view)
        builder.show()
    }

    private fun showDialog() {

        val dialog = Dialog(this)
        var inflater: LayoutInflater = layoutInflater
        var view: View = inflater.inflate(R.layout.add_place_dialog, null)
        var editgetPlace: EditText = view.findViewById(R.id.ed_dialog_get_place)
        var button: Button = view.findViewById(R.id.btn_dialog_add)
        dialog.setContentView(view)
        dialog.show()
        var window: Window = dialog.window
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
        button.setOnClickListener {
            dialog.dismiss()
            progressDialogue.show()
            progressDialogue.setMessage("Fetching data ....")
            var place: String = editgetPlace.text.toString().trim()

            val apiService = ApiClient.getWeather().create(ApiInterface::class.java)
            val call = apiService.getResultByLocation(place, appid)
            call.enqueue(object : Callback<ActionResult> {
                override fun onFailure(call: Call<ActionResult>?, t: Throwable?) {
                    progressDialogue.dismiss()
                    toast(t?.message.toString())
                }

                override fun onResponse(call: Call<ActionResult>, response: Response<ActionResult>) {
                    var isFoundInHawk: Boolean = false
                    progressDialogue.dismiss()
                    if (response.isSuccessful) {
                        val main_object = response.body()
                        var temp: Float = main_object!!.main.temp
                        var tempinC = temp - 273
                        var tempMax: Float = main_object!!.main.tempMax
                        var tempMaxC = tempMax - 273
                        var tempMin: Float = main_object!!.main.tempMin
                        var tempMinC = tempMin - 273
                        var detailModel: DetailModel = DetailModel(main_object.sys.country, main_object!!.name, tempinC, tempMaxC, tempMinC, main_object.wind.speed, main_object.main.humidity,
                                main_object.main.pressure, main_object.weather[0].description, main_object.sys.sunset, main_object.sys.sunrise, main_object.weather[0].icon)

                        if (Hawk.contains("MyKey")) {
                            itemList = Hawk.get("MyKey")
                            for (model in itemList!!) {
                                var location: String = model.placename!!
                                if (place.equals(location, true)) {
                                    isFoundInHawk = true
                                    break
                                }
                            }
                            if (isFoundInHawk) {
                                toast("place already exists")
                                adapter.notifyDataSetChanged()

                            } else {
                                itemList!!.add(detailModel)
                                adapter.addItem(itemList)
                                Hawk.put("MyKey", itemList)
                            }
                        } else {
                            itemList!!.add(detailModel)
                            adapter.addItem(itemList)
                            Hawk.put("MyKey", itemList)
                        }


                    } else if (response.message() == "Not Found") {
                        toast("Invalid place")
                    }
                }
            })
        }

    }


    override fun onStart() {
        super.onStart()
        if (!checkPermissions()) {
            requestPermissions()
        } else {
            if (itemList == null) {
                getLastLocation()
            }

        }
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    View.OnClickListener {
                        // Request permission
                        startLocationPermissionRequest()
                    })
        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest()
        }
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun showSnackbar(mainTextStringId: Int, actionStringId: Int,
                             listener: View.OnClickListener) {
        Snackbar.make(findViewById<View>(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show()
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation()
            } else {
                // Permission denied.
                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.
                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        View.OnClickListener {
                            // Build intent that displays the App settings screen.
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts("package",
                                    BuildConfig.APPLICATION_ID, null)
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        })
            }
        }
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this@AddLocationActivity,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE)
    }


    /**
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     *
     *
     * Note: this method should be called after location permission has been granted.
     */
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        mFusedLocationClient?.getLastLocation()
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful && task.result != null) {
                        var mLastLocation = task.result
                        var lati: Double
                        var longi: Double
                        lati = mLastLocation.latitude
                        longi = mLastLocation.longitude
                        networkHitWithLatLong(lati, longi)
                    } else {
                        Toast.makeText(this@AddLocationActivity, "no_location_detected",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun networkHitWithLatLong(lati: Double, longi: Double) {
        var isFoundInHawk: Boolean = false
        val apiService = ApiClient.getWeather().create(ApiInterface::class.java)
        val call = apiService.getResultByLatLong(lati, longi, appid)
        call.enqueue(object : Callback<ActionResult> {
            override fun onFailure(call: Call<ActionResult>?, t: Throwable?) {
                progressDialogue.dismiss()
                toast(t?.message.toString())
            }

            override fun onResponse(call: Call<ActionResult>, response: Response<ActionResult>) {
                progressDialogue.dismiss()
                if (response.isSuccessful) {
                    val main_object = response.body()
                    var temp: Float = main_object!!.main.temp
                    var tempinC = temp - 273
                    var tempMax: Float = main_object!!.main.tempMax
                    var tempMaxC = tempMax - 273
                    var tempMin: Float = main_object!!.main.tempMin
                    var tempMinC = tempMin - 273
                    var detailModel: DetailModel = DetailModel(main_object.sys.country, main_object.name, tempinC, tempMaxC, tempMinC, main_object.wind.speed, main_object.main.humidity,
                            main_object.main.pressure, main_object.weather[0].description, main_object.sys.sunset, main_object.sys.sunrise, main_object.weather[0].icon)
                    itemList = java.util.ArrayList()
                    if (Hawk.contains("MyKey")) {
                        itemList = Hawk.get("MyKey")
                        for (model in itemList!!) {
                            var location: String = model.placename!!
                            if (main_object!!.name.equals(location, true)) {
                                isFoundInHawk = true
                                break
                            }
                        }
                        if (isFoundInHawk) {
                            toast("place already exists")
                            adapter.notifyDataSetChanged()
                            progressDialogue.dismiss()
                        } else {
                            itemList!!.add(detailModel)
                            adapter.addItem(itemList)
                            Hawk.put("MyKey", itemList)
                        }
                    } else {
                        itemList!!.add(detailModel)
                        adapter.addItem(itemList)
                        Hawk.put("MyKey", itemList)
                    }
                    adapter.setOnListClickListener(object : AddLocationAdapter.OnListClickListener {

                        override fun onItemClick(pos: Int) {
                            detailModel = adapter.getModelAtposition(pos)
                            bnd = Bundle()
                            val intent = Intent(baseContext, DetailActivity::class.java)
                            bnd.putSerializable("LIST", detailModel)
                            intent.putExtras(bnd)
                            startActivity(intent)
                        }

                        override fun onLongPress(pos: Int) {
                            if (itemList!!.size > 1) {
                                showDeleteDialog(pos)
                            }
                        }
                    })
                } else if (response.message() == "Not Found") {
                    toast("Invalid place")
                }
            }
        })
    }

    private fun showDeleteDialog(pos: Int) {
        var alertDialogView: AlertDialog.Builder = AlertDialog.Builder(this)

        alertDialogView.setTitle("Do you really want to delete this item ??")


        alertDialogView.setIcon(android.R.drawable.ic_dialog_alert)


        alertDialogView.setPositiveButton("Ok") { _, _ ->
            adapter.removeItemPosition(pos)
            Thread.sleep(500)
        }

        alertDialogView.setNegativeButton("Cancel") { dialog, _ -> dialog!!.dismiss() }
        alertDialogView.show()

    }


    @Subscribe
    fun onEvent(event: AlertEvent) {
        if (event.isFarienhate) {
                toast("Farenhite")
        } else {
            toast("Celcius")
        }
    }
}
