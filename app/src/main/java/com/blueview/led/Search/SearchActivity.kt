package com.blueview.led.Search

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.blueview.led.R

class SearchActivity: AppCompatActivity() {
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setISfullScreen(this,false)
        setContentView(R.layout.search_activity)
        supportActionBar?.hide()
        searchView=findViewById(R.id.search2)
        //设置该SearchView默认是否自动缩小为图标
        searchView.setIconifiedByDefault(false);
        //设置该SearchView显示搜索按钮
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("查找");
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@SearchActivity,query,Toast.LENGTH_SHORT).show()
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })
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