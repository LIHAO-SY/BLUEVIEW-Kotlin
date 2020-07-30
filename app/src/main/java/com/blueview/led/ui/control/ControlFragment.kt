package com.blueview.led.ui.control

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RadioButton
import com.google.android.material.tabs.TabLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blueview.led.R
import com.blueview.led.ui.control.recyclerview.ControlRecyAdapter
import com.blueview.led.ui.control.recyclerview.ControlRecyData
import androidx.recyclerview.widget.RecyclerView.LayoutManager as RecyclerViewLayoutManager


class ControlFragment : Fragment() {

    private lateinit var dashboardViewModel: ControlViewModel
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
    var handler:Handler= Handler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =ViewModelProvider(this)[ControlViewModel::class.java]
        val root = inflater.inflate(R.layout.fragment_control, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        activity?.let { changeStatusBarTransparent(it) }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        control_tabTitle = view.findViewById(R.id.control_tab_title)
        control_tab1 = view.findViewById(R.id.control_tab1)
        control_tab2 = view.findViewById(R.id.control_tab2)
        control_RecyclerView = view.findViewById(R.id.control_recyclerview)
        recylerList = ArrayList()
        control_tabTitle.addTab(control_tabTitle.newTab())
        control_tab1.addTab(control_tab1.newTab())
        control_tab2.addTab(control_tab2.newTab())
        for (i in 1..10) {
            control_tab1.addTab(control_tab1.newTab())
            control_tab2.addTab(control_tab2.newTab())
            listtab1Title.add("设备$i")
            listtab2Title.add("群组$i")
        }
        listtabTitle.forEachIndexed { i, str ->
            //设置自定义显示小红点的布局
            var tab = control_tabTitle.getTabAt(i)
            tab?.setCustomView(R.layout.tablayoutview)
            tabText = tab?.customView?.findViewById(R.id.tabtext)!!
            tabText?.text = str
        }
        listtab1Title.forEachIndexed { i, str ->
            var tab: TabLayout.Tab? = control_tab1.getTabAt(i)
            tab?.setCustomView(R.layout.tablayoutview)
            tabText = tab?.customView?.findViewById(R.id.tabtext)!!
            tabText?.text = str
        }
        listtab2Title.forEachIndexed { i, str ->
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
        control_RecyclerView.adapter = ControlRecyAdapter(recylerList, control_RecyclerView)
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
    fun changeStatusBarTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        val window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val option = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.decorView.systemUiVisibility = option
            //6.0以上系统支持修改状态栏字体颜色，6.0以下不支持的设为黑色透明背景，突显白色字体
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = Color.parseColor("#00000000")
            }else{
                window.statusBarColor = Color.parseColor("#20000000")
            }
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().setStatusBarColor(getResources().getColor(R.color.bar_color)); //设置状态栏颜色（底色），
            getActivity()?.getWindow()?.getDecorView()?.setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为黑色,看其他文章，说只有黑色和白色
        }
    }


}



