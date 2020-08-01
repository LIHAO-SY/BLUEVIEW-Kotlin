package com.blueview.led.ui.control.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.blueview.led.R

class ControlRecyAdapter(var recylerList:ArrayList<ControlRecyData>,var mRecyclerView: RecyclerView,var root:View): RecyclerView.Adapter<ControlRecyAdapter.ViewHolder>()
{
    private lateinit var context:Context
    private var mRecylerList:ArrayList<ControlRecyData> = recylerList
    private var isStartVisiblity:Boolean=false
    private lateinit var bottom:LinearLayout

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
        var radio:RadioButton=view.findViewById(R.id.control_radio)
        var img:ImageView=view.findViewById(R.id.control_img)
        var card: ConstraintLayout =view.findViewById(R.id.control_recycler_item)
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
        bottom=root.findViewById(R.id.control_bottom)
        holder.radio.setOnClickListener(View.OnClickListener {
            if(!holder.radio.isChecked)
            {
                holder.radio.isChecked=true
            }
            else{
                holder.radio.isChecked=false
            }
        })
        holder.card.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
               if (isStartVisiblity)
               {
                   bottom.visibility=View.GONE
                   setRecyclerChildRadioOrImgisVisiblity(mRecyclerView,false)
                   isStartVisiblity=false
               }
            }
        })
        holder.card.setOnLongClickListener(object :View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                setRecyclerChildRadioOrImgisVisiblity(mRecyclerView,true)
                bottom.visibility=View.VISIBLE
                isStartVisiblity=true
               return true
            }
        })
        mRecyclerView.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(isStartVisiblity)
                {
                    setRecyclerChildRadioOrImgisVisiblity(recyclerView,true)
                }
                else{
                    setRecyclerChildRadioOrImgisVisiblity(recyclerView,false)
                }

            }
        })


    }
    fun setRecyclerChildRadioOrImgisVisiblity(mRecyclerView: RecyclerView,isVisibility: Boolean) {
        for (i in 0..mRecylerList.size) {
            var view: View? = mRecyclerView.layoutManager?.findViewByPosition(i)
            Log.e("TAG", "setRecyclerChildRadioOrImgisVisiblity: $i" )
            var radio: RadioButton? = view?.findViewById(R.id.control_radio)
            var img: ImageView? = view?.findViewById(R.id.control_img)
            if (isVisibility) {
                img?.visibility = View.GONE
                radio?.visibility = View.VISIBLE

            } else {
                img?.visibility = View.VISIBLE
                radio?.visibility = View.GONE
            }
        }
    }
}