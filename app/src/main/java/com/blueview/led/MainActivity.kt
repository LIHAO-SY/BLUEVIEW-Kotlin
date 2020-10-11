package com.blueview.led

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.blueview.led.AiControl.AudioRecognzie
import com.blueview.led.AiControl.TtsControl
import com.blueview.led.Search.SearchActivity
import com.blueview.led.Data.MessageEvent_Aimsg
import com.blueview.led.Util.ScanQRCodeActivity
import com.blueview.led.Util.StatusBarUtil
import com.blueview.led.UI.control.ControlFragment
import com.blueview.led.UI.home.HomeFragment
import com.blueview.led.UI.map.MapFragment
import com.blueview.led.UI.user.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var mic:ImageView
    private lateinit var viewPager: ViewPager
    private lateinit var fragmentPagerAdapter: FragmentPagerAdapter
    private lateinit var fragment_list:ArrayList<Fragment>
    private lateinit var mesageimg:ImageView
    private lateinit var setimg:ImageView
    private lateinit var smimg:ImageView
    val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:Int=1
    private lateinit var search: TextView
    private val audio:AudioRecognzie=AudioRecognzie

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        checkPermissions()
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.setStatusBarDarkTheme(this, true)

        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        //Passing each menu ID as a set of Ids because each
        //menu should be considered as top level destinations.
        //val appBarConfiguration = AppBarConfiguration.Builder( R.id.navigation_home, R.id.navigation_control, R.id.navigation_map,R.id.navigation_user ).build()
        //val navController =Navigation.findNavController(this, R.id.main_fragment)
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        //NavigationUI.setupWithNavController(navView, navController)
        //因此，请用户自行实现CredentialProvider接口

        EventBus.getDefault().register(this)

        viewPager=findViewById(R.id.main_viewpager)
        mesageimg=findViewById(R.id.image_mesag)
        setimg=findViewById(R.id.image_set)
        smimg=findViewById(R.id.imag_sm)
        mic=findViewById(R.id.main_mic)
        search=findViewById(R.id.search_button)
        fragment_list= ArrayList()
        fragment_list.add(HomeFragment())
        fragment_list.add(ControlFragment())
        fragment_list.add(MapFragment())
        fragment_list.add(UserFragment())
        fragmentPagerAdapter=fragmment_adapater(supportFragmentManager, fragment_list)
        viewPager.adapter=fragmentPagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
        navView.setOnNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener,
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_home -> {
                        sethome()
                    }

                    R.id.navigation_control -> {
                        setcontrol()
                    }

                    R.id.navigation_map -> {
                        setmap()
                    }
                    R.id.navigation_user -> {
                        setuser()
                    }

                }
                return false
            }
        })
        viewPager.offscreenPageLimit=3
        search.setOnClickListener {
            val intent=Intent(this,SearchActivity::class.java)
            startActivity(intent)
        }
        smimg.setOnClickListener {
            Thread{
                IntentIntegrator(this).setCaptureActivity(ScanQRCodeActivity::class.java).initiateScan()
                //IntentIntegrator(this).initiateScan()
//                val intent = Intent(this, CaptureActivity::class.java)
//                startActivity(intent)
            }.start()
        }
        mic.setOnClickListener{
            audio.AudioRecognzieInit(this)
        }
    }
    class fragmment_adapater(fm: FragmentManager, var fragment_list: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return fragment_list.size
        }

        override fun getItem(position: Int): Fragment {
            return fragment_list.get(position)
        }
    }

    fun sethome()
    {
        mesageimg.visibility= GONE
        setimg.visibility= GONE
        imag_sm.visibility= VISIBLE
        StatusBarUtil.setStatusBarDarkTheme(this@MainActivity, true)
        viewPager.setCurrentItem(0, true)
    }
    fun setcontrol()
    {
        mesageimg.visibility= GONE
        setimg.visibility= GONE
        imag_sm.visibility= VISIBLE
        StatusBarUtil.setStatusBarDarkTheme(this@MainActivity, true)
        viewPager.setCurrentItem(1, true)
    }
    fun setmap()
    {
        mesageimg.visibility= GONE
        setimg.visibility= GONE
        imag_sm.visibility= GONE
        StatusBarUtil.setStatusBarDarkTheme(this@MainActivity, true)
        viewPager.setCurrentItem(2, true)
    }
    fun setuser()
    {
        smimg.visibility= GONE
        mesageimg.visibility= VISIBLE
        setimg.visibility= VISIBLE
        StatusBarUtil.setStatusBarDarkTheme(this@MainActivity, true)
        viewPager.setCurrentItem(3, true)
    }
    private fun checkPermissions() {
        val permissions: MutableList<String> =LinkedList()
        addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        addPermission(permissions, Manifest.permission.RECORD_AUDIO)
        addPermission(permissions, Manifest.permission.INTERNET)
        addPermission(permissions, Manifest.permission.READ_PHONE_STATE)
        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(
                this, permissions.toTypedArray(),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            )
        }
    }
    private fun addPermission(
        permissionList: MutableList<String>,
        permission: String
    ) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            permissionList.add(permission)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(result != null) {
            if(result.getContents() == null) {
                //Toast.makeText(this, "扫描结果为空", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)//这个是最重要的函数，用来处理接收到的数据，@Subscribe是用来标记线程模式。
    fun MessageEvent(event: MessageEvent_Aimsg)
    {
        search.setText(event.aimsg)
        if(event.aimsg.indexOf("首页")>=0||event.aimsg.indexOf("查询")>=0)
        {
            sethome()
        }else if(event.aimsg.indexOf("控制")>=0)
        {
            setcontrol()
            TtsControl.TtsControlinit(context = this, string = "已打开控制")
        }else if(event.aimsg.indexOf("地图")>=0)
        {
            setmap()
            TtsControl.TtsControlinit(context = this, string = "已打开地图")
        }else if(event.aimsg.indexOf("我的")>=0)
        {
            setuser()
            TtsControl.TtsControlinit(context = this, string = "已打开我的")
        }else{
            //TtsControl.TtsControlinit(context = this, string = "请在试一次，我好像不太懂")
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        audio.release()
    }
}