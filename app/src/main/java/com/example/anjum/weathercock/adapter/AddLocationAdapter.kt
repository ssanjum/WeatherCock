package com.example.anjum.weathercock.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.anjum.weathercock.R
import com.example.anjum.weathercock.model.WeatherModel

/**
 * Created by sanjum on 11/28/2017.
 */
class AddLocationAdapter(var contet: Context, var itemList: ArrayList<WeatherModel>)
    : RecyclerView.Adapter<AddLocationAdapter.MyViewHolder>() {
    lateinit var model: WeatherModel
    override fun getItemCount(): Int {
        return itemList.size
    }

    var listener: OnListClickListener? = null

    interface OnListClickListener {
        fun onItemClick(pos: Int)
    }

    fun setOnListClickListener(listener: OnListClickListener) {
        this.listener = listener

    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        model = itemList.get(position)
        holder!!.place.text = model.place
        holder!!.temp.text = model.temp.toString() + " \u2103"
        holder.linearLayout.setOnClickListener {
            listener?.onItemClick(position)
        }

    }

    fun replaceALL(list: ArrayList<WeatherModel>?) {

        this.itemList.clear()
        itemList.addAll(list!!)
        this.notifyDataSetChanged()
    }

    fun getName(pos: Int): String {

        return itemList[pos].place
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_add_location, parent, false)
        return MyViewHolder(view)
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val place: TextView = itemView!!.findViewById(R.id.tv_item_place)
        val temp: TextView = itemView!!.findViewById(R.id.tv_item_temp)
        val linearLayout = itemView!!.findViewById<LinearLayout>(R.id.ll_item)

    }


}