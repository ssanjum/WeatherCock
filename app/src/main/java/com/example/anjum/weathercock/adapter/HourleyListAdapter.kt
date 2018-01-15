package com.example.anjum.weathercock.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.anjum.weathercock.R
import com.example.anjum.weathercock.R.id.iv_hourley_Image
import com.example.anjum.weathercock.R.id.parent
import com.example.anjum.weathercock.activity.Fitoor
import com.example.anjum.weathercock.model.HourleyModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_actvity.*

/**
 * Created by sanjum on 12/19/2017.
 */
class HourleyListAdapter(val hourleyItemList: ArrayList<HourleyModel>, val context: Context):  RecyclerView.Adapter<HourleyListAdapter.MyViewHolder>()  {
    lateinit var model: HourleyModel



    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        var imageUrl: String = "http://openweathermap.org/img/w/"
        model = hourleyItemList[position]
        var imageId: String = model.imageId!!
        var imageURL=imageUrl+imageId+".png"
        var tempC = Fitoor().convertToTempratureUnit(model.temp, "C")
        var time: String = Fitoor().convertUtcToTime(model.time!!)
        var day: String = Fitoor().convertUtcToDaY(model.time!!)
        holder!!.temp.text=tempC!!.toInt().toString()+Fitoor().ceciliusUnicode
        holder.time.text=time+" , "+day
        Picasso.with(context).load(imageURL).into(holder.imageIcon)
        holder.humidity.text=model.humidity.toString()
    }

    override fun getItemCount(): Int {
        return hourleyItemList.size

    }



    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder? {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_hourley_report, parent, false)
        return HourleyListAdapter.MyViewHolder(view)
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val temp: TextView = itemView!!.findViewById(R.id.tv_hourley_temp)
        val humidity: TextView = itemView!!.findViewById(R.id.tv_hourley_humidity)
        val imageIcon: ImageView = itemView!!.findViewById(R.id.iv_hourley_Image)
        val time: TextView = itemView!!.findViewById(R.id.tv_hourley_time)

    }

    fun replaceAll(itemDailyList: ArrayList<HourleyModel>) {
        this.hourleyItemList.clear()
        this.hourleyItemList.addAll(itemDailyList)
        this.notifyDataSetChanged()
    }
}