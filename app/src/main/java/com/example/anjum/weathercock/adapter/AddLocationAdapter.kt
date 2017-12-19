package com.example.anjum.weathercock.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.anjum.weathercock.R
import com.example.anjum.weathercock.activity.Fitoor
import com.example.anjum.weathercock.model.DetailModel
import com.orhanobut.hawk.Hawk
import org.greenrobot.eventbus.Subscribe

/**
 * Created by sanjum on 11/28/2017.
 */
class AddLocationAdapter(var itemList: ArrayList<DetailModel>, val ctx: Context)
    : RecyclerView.Adapter<AddLocationAdapter.MyViewHolder>() {
    lateinit var model: DetailModel
    override fun getItemCount(): Int {
        return itemList.size
    }

    var listener: OnListClickListener? = null

    interface OnListClickListener {
        fun onItemClick(pos: Int)
        fun onLongPress(pos: Int)
    }

    fun setOnListClickListener(listener: OnListClickListener) {
        this.listener = listener

    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        model = itemList[position]
        holder!!.place.text = model.placename
        holder.temp.text = model.temp!!.toInt().toString() + Fitoor().ceciliusUnicode
        holder!!.linearLayout.setOnClickListener {
            listener!!.onItemClick(position)
        }
        holder.linearLayout.setOnLongClickListener {
            listener!!.onLongPress(position)
            true
        }


    }

    fun getModelAtposition(pos: Int): DetailModel {
        return itemList[pos]
    }

    fun addItem(list: ArrayList<DetailModel>?) {
        itemList.clear()
        itemList.addAll(list!!)
        notifyDataSetChanged()
        Hawk.put("MyKey", itemList)
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_add_location, parent, false)
        return MyViewHolder(view)
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val place: TextView = itemView!!.findViewById(R.id.tv_item_place)
        val temp: TextView = itemView!!.findViewById(R.id.tv_item_temp)
        val linearLayout = itemView!!.findViewById<LinearLayout>(R.id.ll_item)!!

    }

    fun removeItemPosition(pos: Int) {
        itemList.removeAt(pos)
        Hawk.put("MyKey", itemList)
        this.notifyDataSetChanged()
    }




}