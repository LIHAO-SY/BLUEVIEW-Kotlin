package com.blueview.led.UI.user

import android.content.Intent
import android.os.Bundle
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blueview.led.R
import com.blueview.led.UI.Plan.PlanActivity
import com.google.android.material.tabs.TabLayout
import java.util.*
import kotlin.collections.ArrayList

class UserFragment: Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var tabLayout: TabLayout
    private lateinit var tabtext: TextView
    private lateinit var user_listview:ListView
    private lateinit var simple_Adapter:SimpleAdapter
    private val dataList : ArrayList<Map<String, Any>>? = ArrayList()//SimpleAdapter中的数据源
    private var imgList:MutableList<Int> = mutableListOf(R.drawable.mygroup,R.drawable.ledlight,R.drawable.timecontrol,R.drawable.set)
    private var stringList:MutableList<String> = mutableListOf("我的群组","一键调光","我的计划","设置")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        userViewModel=ViewModelProvider(this)[UserViewModel::class.java]
        val root = inflater.inflate(R.layout.fragment_user,container,false)
        userViewModel.text.observe(viewLifecycleOwner,Observer{})
        //activity?.let { changeStatusBarTransparent(it) }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayout=view.findViewById(R.id.user_tab_title)
        user_listview=view.findViewById(R.id.user_listview)
        var listFragment: MutableList<String> = mutableListOf("我的")
        listFragment.forEachIndexed { i, str ->
            tabLayout.addTab(tabLayout.newTab())
            var tab = tabLayout.getTabAt(i)
            tab?.setCustomView(R.layout.tablayoutview)
            tabtext = tab?.customView?.findViewById(R.id.tabtext)!!
            tabtext?.text = str
        }
        val view: View? = tabLayout.getChildAt(0)
        var tabtext: TextView = view?.findViewById(R.id.tabtext)!!
        tabtext?.setTextSize(20f)
        val paint: TextPaint = tabtext.getPaint()
        paint.isFakeBoldText = true
        simple_Adapter = SimpleAdapter(context, getData(), R.layout.userlistitem, arrayOf("pic", "text"), intArrayOf(R.id.imageView_user, R.id.textView_user))
        user_listview.setAdapter(simple_Adapter) //加载SimpleAdapter适配器
        user_listview.setOnItemClickListener { parent, view, position, id ->
            if (position==2)
            {
                val intent=Intent(context,PlanActivity::class.java)
                context?.startActivity(intent)
            }
        }

    }
     fun getData(): List<Map<String, Any>>? {
        for (i in stringList.indices) {
            val map: MutableMap<String, Any> = HashMap()
            map["pic"] = imgList.get(i)
            map["text"] = stringList.get(i)
            dataList?.add(map)
        }
        return dataList
    }
}


