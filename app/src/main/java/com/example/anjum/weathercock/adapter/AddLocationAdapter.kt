package com.example.anjum.weathercock.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.anjum.weathercock.R
import com.example.anjum.weathercock.model.WeatherModel

/**
 * Created by sanjum on 11/28/2017.
 */
class AddLocationAdapter(contet: Context, itemList: ArrayList<WeatherModel>) : RecyclerView.Adapter<AddLocationAdapter.MyViewHolder>() {
    lateinit var itemList: ArrayList<WeatherModel>

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_add_location, parent, false)
        return MyViewHolder(view)
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {


    }


}