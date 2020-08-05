package com.blueview.led

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.blueview.led.ui.ControlEqment.ControlEqmentLight
import com.blueview.led.ui.ControlEqment.ControlEqmentRgb

class ControlEqmentActivity: AppCompatActivity() {

    private lateinit var controlEqmentText:TextView
    private lateinit var epment_viewpager:ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarTransparent2(this)
        setContentView(R.layout.controleqment_activity)
        supportActionBar?.hide()
        var intent=intent
        var id= intent.getIntExtra("id",9999)
        controlEqmentText=findViewById(R.id.textView_ledqment)
        epment_viewpager=findViewById(R.id.epment_viewPager)
        var listFragment: MutableList< Fragment> = mutableListOf( ControlEqmentLight(), ControlEqmentRgb()
        )
        //关键在于先关联viewpager，后修改tab布局（注意在绑定了viewpager后tablayout的tab已经被设置了，所以做修改操作就好了）
        epment_viewpager.adapter = fragmment_adapater(supportFragmentManager, listFragment)
        controlEqmentText.text=id.toString()

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