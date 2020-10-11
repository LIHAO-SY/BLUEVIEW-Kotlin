package com.blueview.led.UI.control

import android.graphics.Color
import android.os.Bundle
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blueview.led.R
import com.blueview.led.UI.control.recyclerview.ControlRecyAdapter
import com.blueview.led.UI.control.recyclerview.PlanRecyAdapter
import com.blueview.led.UI.control.recyclerview.ControlRecyData


class ControlFragment : Fragment() {

    private lateinit var control_tabTitle: TabLayout
    private lateinit var control_tab1:TabLayout
    private lateinit var control_tab2:TabLayout
    private lateinit var tabText:TextView
    private lateinit var control_RecyclerView:RecyclerView
    private lateinit var linerlayoutManager: LinearLayoutManager
    private lateinit var recylerList:ArrayList<ControlRecyData>

    private var listtabTitle: MutableList<String> = mutableListOf("控制")
    private var listtab1Title:MutableList<String> = mutableListOf("全部")
    private var listtab2Title:MutableList<String> = mutableListOf("全部")
    private lateinit var root:View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_control, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        control_tabTitle = view.findViewById(R.id.control_tab_title)
        control_tab1 = view.findViewById(R.id.control_tab1)
        control_tab2 = view.findViewById(R.id.control_tab2)
        control_RecyclerView = view.findViewById(R.id.control_recyclerview)
        recylerList = ArrayList()
        for (i in 1..10) {
            listtab1Title.add("设备$i")
            listtab2Title.add("群组$i")
        }
        listtabTitle.forEachIndexed { i, str ->
            control_tabTitle.addTab(control_tabTitle.newTab())
            var tab = control_tabTitle.getTabAt(i)
            tab?.setCustomView(R.layout.tablayoutview)
            tabText = tab?.customView?.findViewById(R.id.tabtext)!!
            tabText?.text = str
        }
        listtab1Title.forEachIndexed { i, str ->
            control_tab1.addTab(control_tab1.newTab())
            var tab: TabLayout.Tab? = control_tab1.getTabAt(i)
            tab?.setCustomView(R.layout.tablayoutview)
            tabText = tab?.customView?.findViewById(R.id.tabtext)!!
            tabText?.text = str
        }
        listtab2Title.forEachIndexed { i, str ->

            control_tab2.addTab(control_tab2.newTab())
            var tab: TabLayout.Tab? = control_tab2.getTabAt(i)
            tab?.setCustomView(R.layout.tablayoutview)
            tabText = tab?.customView?.findViewById(R.id.tabtext)!!
            tabText?.text = str
        }
        initTab()
        control_tabTitle.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val view: View? = tab?.getCustomView()
                var tabtext: TextView = view?.findViewById(R.id.tabtext)!!
                tabtext?.setTextSize(18f)
                val paint: TextPaint = tabtext.getPaint()
                paint.isFakeBoldText = false
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val view: View? = tab?.getCustomView()
                var tabtext: TextView = view?.findViewById(R.id.tabtext)!!
                tabtext?.setTextSize(20f)
                val paint: TextPaint = tabtext.getPaint()
                paint.isFakeBoldText = true
            }
        })

        control_tab1.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val view: View? = tab?.getCustomView()
                var tabtext: TextView = view?.findViewById(R.id.tabtext)!!
                tabtext?.setTextColor(Color.BLACK)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val view: View? = tab?.getCustomView()
                var tabtext: TextView = view?.findViewById(R.id.tabtext)!!
                tabtext?.setTextColor(Color.parseColor("#03DAC5"))
            }
        })

        control_tab2.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val view: View? = tab?.getCustomView()
                var tabtext: TextView = view?.findViewById(R.id.tabtext)!!
                tabtext?.setTextColor(Color.BLACK)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val view: View? = tab?.getCustomView()
                var tabtext: TextView = view?.findViewById(R.id.tabtext)!!
                tabtext?.setTextColor(Color.parseColor("#03DAC5"))
            }
        })
        for (i in 1..10) {
            recylerList.add(ControlRecyData("主控器$i", "状态：正常$i", "功率：10$i", "群组：2$i"))
        }
        linerlayoutManager = LinearLayoutManager(context)
        control_RecyclerView.layoutManager = linerlayoutManager
        control_RecyclerView.adapter = ControlRecyAdapter(recylerList, control_RecyclerView,root)
    }
    fun initTab() {
        var mtexttab: TextView
        var view: View
        //Title
        view = control_tabTitle.getTabAt(0)?.getCustomView()!!
        mtexttab = view?.findViewById(R.id.tabtext)!!
        mtexttab?.setTextSize(20f)
        val paint: TextPaint = mtexttab.getPaint()
        paint.isFakeBoldText = true
        //Tab1
        view = control_tab1.getTabAt(0)?.getCustomView()!!
        mtexttab = view?.findViewById(R.id.tabtext)!!
        mtexttab?.setTextColor(Color.parseColor("#03DAC5"))
        //Tab2
        view = control_tab2.getTabAt(0)?.getCustomView()!!
        mtexttab = view?.findViewById(R.id.tabtext)!!
        mtexttab?.setTextColor(Color.parseColor("#03DAC5"))
    }



}



