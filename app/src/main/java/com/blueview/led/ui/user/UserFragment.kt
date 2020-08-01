package com.blueview.led.ui.user

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blueview.led.R

class UserFragment: Fragment() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        userViewModel=ViewModelProvider(this)[UserViewModel::class.java]
        val root = inflater.inflate(R.layout.fragment_user,container,false)
        userViewModel.text.observe(viewLifecycleOwner,Observer{})
        activity?.let { changeStatusBarTransparent(it) }
        return root
    }
    fun changeStatusBarTransparent(activity: Activity) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            return
//        }
//        val window = activity.window
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            val option = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            window.decorView.systemUiVisibility = option
//            //6.0以上系统支持修改状态栏字体颜色，6.0以下不支持的设为黑色透明背景，突显白色字体
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                window.statusBarColor = Color.parseColor("#00000000")
//            }else{
//                window.statusBarColor = Color.parseColor("#20000000")
//            }
//        } else {
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity()?.getWindow()?.setStatusBarColor(getResources().getColor(R.color.bar_color)); //设置状态栏颜色（底色），
            getActivity()?.getWindow()?.getDecorView()?.setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为黑色,看其他文章，说只有黑色和白色
        }
    }
}