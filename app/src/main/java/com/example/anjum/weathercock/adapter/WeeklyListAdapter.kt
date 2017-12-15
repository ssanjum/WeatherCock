package com.example.anjum.weathercock.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.anjum.weathercock.R
import com.example.anjum.weathercock.helper.WeatherHelper
import com.example.anjum.weathercock.model.DetailModel

/**
 * Created by sanjum on 12/13/2017.
 */
class WeeklyListAdapter(val dailyItemList: ArrayList<DetailModel>) : RecyclerView.Adapter<WeeklyListAdapter.MyViewHolder>() {
    lateinit var model: DetailModel

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        model = dailyItemList[position]
        var day: String = WeatherHelper().convertUtcToDaY(model.sunset!!)

        holder!!.day.text = day
        holder!!.temp.text = model.temp.toString()
        holder!!.humidity.text = model.humidity.toString()
        holder!!.pressure.text = model.pressure.toString()
        holder!!.temp_max.text = model.tempMax.toString()
        holder!!.temp_min.text = model.tempMin.toString()
        holder.description.text=model.description

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_daily_report, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dailyItemList.size
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView!!.findViewById(R.id.tv_daily_day)
        val temp: TextView = itemView!!.findViewById(R.id.tv_daily_temp)
        val humidity: TextView = itemView!!.findViewById(R.id.tv_daily_humid)
        val pressure: TextView = itemView!!.findViewById(R.id.tv_daily_pressure)
        val temp_max: TextView = itemView!!.findViewById(R.id.tv_daily_max_temp)
        val temp_min: TextView = itemView!!.findViewById(R.id.tv_daily_min_temp)
        val description: TextView = itemView!!.findViewById(R.id.tv_daily_description)


    }

    fun replaceAll(itemDailyList: ArrayList<DetailModel>) {
        this.dailyItemList.clear()
        this.dailyItemList.addAll(itemDailyList)
        this.notifyDataSetChanged()
    }
}
