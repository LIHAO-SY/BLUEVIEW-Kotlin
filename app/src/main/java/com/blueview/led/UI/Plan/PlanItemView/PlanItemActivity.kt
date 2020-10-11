package com.blueview.led.UI.Plan.PlanItemView

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.blueview.led.R
import com.blueview.led.UI.Plan.PlanItemView.recyclerview.PlanItemRecyAdapter
import com.blueview.led.UI.control.recyclerview.PlanItemRecyData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yanzhenjie.recyclerview.OnItemMenuClickListener
import com.yanzhenjie.recyclerview.SwipeMenu
import com.yanzhenjie.recyclerview.SwipeMenuCreator
import com.yanzhenjie.recyclerview.SwipeMenuItem
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration

class PlanItemActivity: AppCompatActivity()
{
    private lateinit var float_add: FloatingActionButton
    private lateinit var plan_recyclryview:SwipeRecyclerView
    private lateinit var arrayList: ArrayList<PlanItemRecyData>
    private lateinit var  adapter:PlanItemRecyAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planitem)
        supportActionBar?.hide()
        setISfullScreen(this, false)

        float_add=findViewById(R.id.planItemfloatingActionButton)
        plan_recyclryview=findViewById(R.id.plan_item_swiperecyclerview)

        float_add.setOnClickListener {
           showTimePick()
        }

        val itemDecoration= DefaultItemDecoration(R.color.bar_color)
        //plan_recyclryview.setLongPressDragEnabled(true); // 拖拽排序，默认关闭。
        //plan_recyclryview.setItemViewSwipeEnabled(true); // 侧滑删除，默认关闭。
        plan_recyclryview.useDefaultLoadMore();


        val linerlayoutManager = LinearLayoutManager(this)
        plan_recyclryview.layoutManager = linerlayoutManager
        arrayList=ArrayList()
        for (i in 1..10)
        {
            arrayList.add(PlanItemRecyData("时间"+i.toString(),"亮度"+i.toString()))
        }
        adapter=PlanItemRecyAdapter(arrayList, plan_recyclryview)

        val swipeMenuCreator= SwipeMenuCreator { leftMenu: SwipeMenu, rightMenu: SwipeMenu, i: Int ->
            val swipeMenuItem= SwipeMenuItem(this)
                .setText("删除")
                .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                .setTextColor(Color.WHITE)
                .setWidth(250)
                .setBackground(R.color.red)
            rightMenu.addMenuItem(swipeMenuItem)

        }
        plan_recyclryview.setSwipeMenuCreator(swipeMenuCreator)
        val mItemMenuClickListener = OnItemMenuClickListener { menuBridge, position -> // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu()

            // 左侧还是右侧菜单：
            val direction = menuBridge.direction
            // 菜单在Item中的Position：
            val menuPosition = menuBridge.position
            adapter.notifyItemRemoved(position)
            arrayList.removeAt(position)
            adapter.notifyItemRangeChanged(position,adapter.itemCount-position)
            Log.e("arrayList-size",arrayList.size.toString())
            //Toast.makeText(this,position.toString(),Toast.LENGTH_SHORT).show()
        }
        plan_recyclryview.setOnItemMenuClickListener(mItemMenuClickListener)
        plan_recyclryview.adapter =adapter
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun showTimePick()
    {
        var time:String=""
        var light:String="50%"
        val view= LayoutInflater.from(this).inflate(R.layout.dialog_planitem_edit,null)
        val seekBar=view.findViewById<SeekBar>(R.id.plan_seekbar)
        val editText=view.findViewById<EditText>(R.id.plan_edittext)
        val timePicker=view.findViewById<TimePicker>(R.id.timepicker)
        time=timePicker.hour.toString()+":"+timePicker.minute.toString()

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                editText.setText(progress.toString()+"%")
                light=progress.toString()+"%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            time=hourOfDay.toString()+":"+minute.toString()
        }

        var alert= AlertDialog.Builder(this)
        //timePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);  //设置点击事件不弹键盘
        timePicker.setIs24HourView(true);   //设置时间显示为24小时
        alert.setView(view)
        alert.setPositiveButton("确认", DialogInterface.OnClickListener { dialog, which ->
            arrayList.add(PlanItemRecyData(time,light))
            adapter.notifyItemChanged(arrayList.size)
        })
        alert.setNegativeButton("取消",null)
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

