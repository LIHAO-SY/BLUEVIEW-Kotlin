package com.blueview.led.UI.control.recyclerview

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.blueview.led.UI.ControlEqment.ControlEqmentActivity
import com.blueview.led.R
import com.blueview.led.UI.Plan.PlanItemView.PlanItemActivity

class PlanRecyAdapter(var recylerList:ArrayList<PlanRecyData>, var mRecyclerView: RecyclerView): RecyclerView.Adapter<PlanRecyAdapter.ViewHolder>()
{
    private lateinit var context:Context
    private var mRecylerList:ArrayList<PlanRecyData> = recylerList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanRecyAdapter.ViewHolder
    {
        context=parent.context
        var view:View=LayoutInflater.from(context).inflate(R.layout.activity_plan_item,parent,false)
        return  ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        var text1:TextView=view.findViewById(R.id.text_plan)
        var onclick_plan:ConstraintLayout=view.findViewById(R.id.onclick_plan)

    }

    override fun getItemCount(): Int
    {
        return mRecylerList.size
    }

    override fun onBindViewHolder(holder: PlanRecyAdapter.ViewHolder, position: Int) {

        holder.text1.text=mRecylerList[position].getstr1()

        holder.onclick_plan.setOnClickListener(View.OnClickListener
        {
            var intent=Intent(context,PlanItemActivity::class.java)
            context.startActivity(intent)
        })


    }

}