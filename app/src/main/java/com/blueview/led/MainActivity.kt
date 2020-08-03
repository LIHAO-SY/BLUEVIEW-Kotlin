package com.blueview.led

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.blueview.led.ui.control.ControlFragment
import com.blueview.led.ui.home.HomeFragment
import com.blueview.led.ui.map.MapFragment
import com.blueview.led.ui.user.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var fragmentPagerAdapter: FragmentPagerAdapter
    private lateinit var fragment_list:ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setISfullScreen(this,false)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration.Builder(
//            R.id.navigation_home, R.id.navigation_control, R.id.navigation_map,R.id.navigation_user
//        )
//            .build()
//        val navController =Navigation.findNavController(this, R.id.main_fragment)
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
//        NavigationUI.setupWithNavController(navView, navController)
        viewPager=findViewById(R.id.main_viewpager)

        fragment_list= ArrayList()
        fragment_list.add(HomeFragment())
        fragment_list.add(ControlFragment())
        fragment_list.add(MapFragment())
        fragment_list.add(UserFragment())
        fragmentPagerAdapter=fragmment_adapater(supportFragmentManager,fragment_list)
        viewPager.adapter=fragmentPagerAdapter
        viewPager.addOnPageChangeListener(object:ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                navView.menu.getItem(position).setChecked(true)
            }
        })

        navView.setOnNavigationItemSelectedListener(object :NavigationView.OnNavigationItemSelectedListener,
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId)
                {
                    R.id.navigation_home->
                    {
                        setISfullScreen(this@MainActivity,false)
                        viewPager.setCurrentItem(0,true)
                    }

                    R.id.navigation_control->
                    {
                        setISfullScreen(this@MainActivity,false)
                        viewPager.setCurrentItem(1,true)
                    }

                    R.id.navigation_map->
                    {
                        setISfullScreen(this@MainActivity,true)
                        viewPager.setCurrentItem(2,true)
                    }
                    R.id.navigation_user->
                    {
                        setISfullScreen(this@MainActivity,false)
                        viewPager.setCurrentItem(3,true)
                    }

                }
                return false
            }
        })
        viewPager.offscreenPageLimit=3
//        viewPager.setOnTouchListener(object : View.OnTouchListener {
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean
//            {
//                return true
//            }
//        })




    }
    class fragmment_adapater(fm: FragmentManager ,var fragment_list:ArrayList<Fragment>) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return fragment_list.size
        }

        override fun getItem(position: Int): Fragment {
            return fragment_list.get(position)
        }
    }
    fun changeStatusBarTransparent1(activity: Activity) {
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
    fun setISfullScreen(activity: Activity,isFullScreen:Boolean) {
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


    object test {

        private lateinit var viewpag:ViewPager
        fun setviewpager(mView:ViewPager)
        {
            viewpag=mView
        }
        fun getviewpag():ViewPager
        {
            return viewpag
        }
    }
}
