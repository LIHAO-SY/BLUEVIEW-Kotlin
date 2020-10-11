package com.blueview.led.UI.ControlEqment

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager

import com.blueview.led.R
import com.blueview.led.Data.MessageEvent_Light
import com.blueview.led.Data.MessageEvent_Rgb
import com.blueview.led.UI.ViewPager.NoScrollViewPager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ControlEqmentActivity: AppCompatActivity() {

    private lateinit var controlEqmentText:TextView
    private lateinit var epment_viewpager: NoScrollViewPager
    private lateinit var ledlight_prog: SeekBar
    private lateinit var ledcoloreq_prog: SeekBar
    private lateinit var imag_left:ImageView
    private lateinit var imag_right:ImageView
    private lateinit var back_text:ImageView
    private lateinit var card:CardView
    var viewpagerInt:Int=0
    private lateinit var arrayString: ArrayList<String>
    private lateinit var spinner: Spinner


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.controleqment_activity)
        supportActionBar?.hide()
        setISfullScreen(this,false)
        var intent=intent
        var id= intent.getIntExtra("id",9999)
        controlEqmentText=findViewById(R.id.textView_ledqment)
        epment_viewpager=findViewById(R.id.epment_viewPager)
        ledlight_prog=findViewById(R.id.SeekBar_ledlight)
        ledcoloreq_prog=findViewById(R.id.SeekBar_ledcoloreq)
        imag_left=findViewById(R.id.imageview_eq_left)
        imag_right=findViewById(R.id.imageview_eq_right)
        back_text=findViewById(R.id.control_back)
        card=findViewById(R.id.control_cad)
        spinner=findViewById(R.id.planspinner)

        arrayString=ArrayList()
        for (i in 1..4)
        {
            arrayString.add("计划"+i)
        }
        val adapter= ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arrayString);
        spinner.setAdapter(adapter);

        var listFragment: MutableList< Fragment> = mutableListOf( ControlEqmentLight(), ControlEqmentRgb()
        )
        //关键在于先关联viewpager，后修改tab布局（注意在绑定了viewpager后tablayout的tab已经被设置了，所以做修改操作就好了）
        epment_viewpager.adapter =
            fragmment_adapater(
                supportFragmentManager,
                listFragment
            )
        controlEqmentText.text=id.toString()
        ledlight_prog.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                EventBus.getDefault().post(
                    MessageEvent_Light(progress.toString())
                )//MainActivty是一个发布者(publisher)的角色，不用注册，直接调用post()发送即
                Log.e("progress","$progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        ledcoloreq_prog.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        epment_viewpager.setCurrentItem(0,true)
        epment_viewpager.setScroll(true)
        epment_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
                if (state === 1 || state === 2) {
                    imag_right.visibility = View.GONE
                    imag_left.visibility = View.GONE
                } else {
                    imag_right.visibility = View.VISIBLE
                    imag_left.visibility = View.VISIBLE
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

            }
        })
        imag_left.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                if (viewpagerInt>1)
                {
                    viewpagerInt=0
                }
                epment_viewpager.setCurrentItem(viewpagerInt)
                viewpagerInt++;
            }
        })
        imag_right.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                if (viewpagerInt<0)
                {
                    viewpagerInt=1
                }
                epment_viewpager.setCurrentItem(viewpagerInt)
                viewpagerInt--;
            }

        })
        back_text.setOnClickListener {
            finish()
        }
        EventBus.getDefault().register(this)

//        epment_viewpager.setOnTouchListener(object :View.OnTouchListener{
//            @SuppressLint("ClickableViewAccessibility")
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                when(event?.action)
//                {
//                   MotionEvent.ACTION_DOWN->
//                   {
//                       imag_right.visibility=View.GONE
//                       imag_left.visibility=View.GONE
//                       Log.e("viewpagerMotionEvent","ACTION_DOWN")
//                   }
//                    MotionEvent.ACTION_UP->
//                    {
//                        imag_right.visibility=View.VISIBLE
//                        imag_left.visibility=View.VISIBLE
//                        Log.e("viewpagerMotionEvent","ACTION_UP")
//                    }
//                }
//                return false
//            }
//        })
    }
    @Subscribe(threadMode = ThreadMode.MAIN)//这个是最重要的函数，用来处理接收到的数据，@Subscribe是用来标记线程模式。
    fun onMessageEvent(eventLight: MessageEvent_Rgb)//线程具体内容可自行查找，大部分的都用ThreadMode.MAIN，应为更新UI都是在主线程
    {
        card.setBackgroundColor(Color.parseColor(eventLight.rgb))
    }
    class fragmment_adapater(fm: FragmentManager ,var fragment_list:MutableList<Fragment>) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return fragment_list.size
        }

        override fun getItem(position: Int): Fragment {
            return fragment_list.get(position)
        }
    }
    fun setISfullScreen(activity: Activity, isFullScreen:Boolean) {
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
                getWindow()?.getDecorView()?.setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为黑色,看其他文章，说只有黑色和白色
            }
        }
    }

}

