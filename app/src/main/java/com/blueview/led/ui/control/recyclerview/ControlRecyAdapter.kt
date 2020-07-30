package com.blueview.led.ui.control.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blueview.led.R

class ControlRecyAdapter(var recylerList:ArrayList<ControlRecyData>): RecyclerView.Adapter<ControlRecyAdapter.ViewHolder>()
{
    private lateinit var context:Context

    private var mRecylerList:ArrayList<ControlRecyData> = recylerList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ControlRecyAdapter.ViewHolder
    {
        context=parent.context
        var view:View=LayoutInflater.from(context).inflate(R.layout.control_recyview_item,parent,false)
        return  ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var text1:TextView=view.findViewById(R.id.cotrol_recyview_itme_textTitle)
        var text2:TextView=view.findViewById(R.id.cotrol_recyview_itme_text1)
        var text3:TextView=view.findViewById(R.id.cotrol_recyview_itme_text2)
        var text4:TextView=view.findViewById(R.id.cotrol_recyview_itme_text3)
    }

    override fun getItemCount(): Int
    {
       return mRecylerList.size
    }

    override fun onBindViewHolder(holder: ControlRecyAdapter.ViewHolder, position: Int) {
        holder.text1.text=mRecylerList[position].getstr1()
        holder.text2.text=mRecylerList[position].getstr2()
        holder.text3.text=mRecylerList[position].getstr3()
        holder.text4.text=mRecylerList[position].getstr4()
    }
}