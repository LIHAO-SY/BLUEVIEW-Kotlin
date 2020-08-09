package com.blueview.led.ui.ControlEqment

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.blueview.led.R
import com.blueview.led.Util.MessageEvent
import kotlinx.android.synthetic.main.controleqment_activity.*
import org.greenrobot.eventbus.EventBus

class ControlEqmentActivity: AppCompatActivity() {

    private lateinit var controlEqmentText:TextView
    private lateinit var epment_viewpager:ViewPager
    private lateinit var ledlight_prog: SeekBar
    private lateinit var ledcoloreq_prog: SeekBar
    private lateinit var imag_left:ImageView
    private lateinit var imag_right:ImageView

    var viewpagerInt:Int=0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarTransparent2(this)
        setContentView(R.layout.controleqment_activity)
        supportActionBar?.hide()
        var intent=intent
        var id= intent.getIntExtra("id",9999)
        controlEqmentText=findViewById(R.id.textView_ledqment)
        epment_viewpager=findViewById(R.id.epment_viewPager)
        ledlight_prog=findViewById(R.id.SeekBar_ledlight)
        ledcoloreq_prog=findViewById(R.id.SeekBar_ledcoloreq)
        imag_left=findViewById(R.id.imageview_eq_left)
        imag_right=findViewById(R.id.imageview_eq_right)

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
                EventBus.getDefault().post(MessageEvent(progress.toString()))//MainActivty是一个发布者(publisher)的角色，不用注册，直接调用post()发送即
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
        epment_viewpager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
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

    class fragmment_adapater(fm: FragmentManager ,var fragment_list:MutableList<Fragment>) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return fragment_list.size
        }

        override fun getItem(position: Int): Fragment {
            return fragment_list.get(position)
        }
    }
    fun changeStatusBarTransparent2(activity: Activity) {
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
            getWindow()?.getDecorView()?.setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为黑色,看其他文章，说只有黑色和白色
        }
    }

}