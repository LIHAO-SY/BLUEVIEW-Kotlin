package com.blueview.led.UI.Plan

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import androidx.recyclerview.widget.LinearLayoutManager
import com.blueview.led.R
import com.blueview.led.UI.control.recyclerview.PlanRecyAdapter
import com.blueview.led.UI.control.recyclerview.PlanRecyData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yanzhenjie.recyclerview.*


class PlanActivity:AppCompatActivity() {

    private lateinit var float_add: FloatingActionButton
    private lateinit var plan_recyclryview:SwipeRecyclerView
    private lateinit var arrayList: ArrayList<PlanRecyData>
    private lateinit var adapter: PlanRecyAdapter

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan)
        supportActionBar?.hide()
        setISfullScreen(this, false)
        float_add=findViewById(R.id.plan_floatingActionButton)
        plan_recyclryview=findViewById(R.id.plan_swiperrecyclerview)

        float_add.setOnClickListener {

            Add_or_edit_Plan(isadd = true)
        }
        val swipeMenuCreator=SwipeMenuCreator { leftMenu: SwipeMenu, rightMenu: SwipeMenu, i: Int ->
            val swipeMenuItem1=SwipeMenuItem(this)
                .setText("删除")
                .setHeight(MATCH_PARENT)
                .setTextColor(Color.WHITE)
                .setWidth(250)
                .setBackground(R.color.red)
            val swipeMenuItem2=SwipeMenuItem(this)
                .setText("重命名")
                .setHeight(MATCH_PARENT)
                .setTextColor(Color.WHITE)
                .setWidth(250)
                .setBackground(R.color.yellow)
            rightMenu.addMenuItem(swipeMenuItem2)
            rightMenu.addMenuItem(swipeMenuItem1)
        }

        val linerlayoutManager = LinearLayoutManager(this)
        plan_recyclryview.layoutManager = linerlayoutManager
        arrayList=ArrayList()
        for (i in 1..10)
        {
            arrayList.add(PlanRecyData("计划" + i.toString()))
        }
        adapter=PlanRecyAdapter(arrayList, plan_recyclryview)
        plan_recyclryview.setSwipeMenuCreator(swipeMenuCreator)
        val mItemMenuClickListener = OnItemMenuClickListener { menuBridge, position -> // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu()
            // 左侧还是右侧菜单：
            val direction = menuBridge.direction
            // 菜单在Item中的Position：
            val menuPosition = menuBridge.position
            if (menuPosition==1)
            {
                adapter.notifyItemRemoved(position)
                arrayList.removeAt(position)
                //adapter.notifyDataSetChanged()
                adapter.notifyItemRangeChanged(position, adapter.itemCount - position)
                Log.e("arrayList-size", arrayList.size.toString())
            }
            if (menuPosition==0)
            {
                Add_or_edit_Plan(position,isadd = false)
            }

        }
        plan_recyclryview.setLongPressDragEnabled(false); // 拖拽排序，默认关闭。
        plan_recyclryview.setItemViewSwipeEnabled(false); // 侧滑删除，默认关闭。
        plan_recyclryview.setOnItemMenuClickListener(mItemMenuClickListener)

        plan_recyclryview.adapter = adapter
    }
    fun Add_or_edit_Plan(vararg position: Int, isadd:Boolean )
    {
        val alert:AlertDialog.Builder=AlertDialog.Builder(this)
        val view=layoutInflater.inflate(R.layout.dialog_plan_edit,null)
        val editText=view.findViewById<EditText>(R.id.plan_edit)
        alert.setView(view)
        if (isadd)
        {
            editText.setText("计划"+(arrayList.size+1).toString())
        }else{
            editText.setText(arrayList[position[0]].getstr1())
        }

        alert.setTitle("添加计划")
        alert.setPositiveButton("确定", DialogInterface.OnClickListener { dialog, which ->
            val title = editText.text.toString()
            if (!title.equals("") && title != null) {
                if (isadd) {
                    arrayList.add(PlanRecyData(title))
                    adapter.notifyItemChanged(arrayList.size)
                } else {
                    arrayList.set(position[0], PlanRecyData(title))
                    adapter.notifyItemChanged(position[0])
                }

            } else {
                Toast.makeText(this, "名称不能为空", Toast.LENGTH_SHORT).show()
            }

        })
        alert.setNegativeButton("取消", null)
        alert.create().show()
    }
    fun setISfullScreen(activity: Activity, isFullScreen: Boolean) {
        if (Build.VERSION.SDK_INT >= 21) { //21表示5.0
            val window: Window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            )
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(Color.TRANSPARENT)
        } else if (Build.VERSION.SDK_INT >= 19) { //19表示4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if(!isFullScreen)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //getWindow().setStatusBarColor(getResources().getColor(R.color.bar_color)); //设置状态栏颜色（底色），
                getWindow()?.getDecorView()?.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为黑色,看其他文章，说只有黑色和白色
            }
        }
    }
}