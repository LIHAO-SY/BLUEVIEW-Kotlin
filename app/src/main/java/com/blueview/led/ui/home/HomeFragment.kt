package com.blueview.led.ui.home

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.blueview.led.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
   // private lateinit var tabLayout: TabLayout
    private lateinit var home_viewpag: ViewPager
    private lateinit var fragment_list: ArrayList<Fragment>
    private lateinit var fragmentPagerAdapter: FragmentPagerAdapter
    private lateinit var tabtext:TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        activity?.let { changeStatusBarTransparent(it) }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //tabLayout = view.findViewById(R.id.tab_title)
        home_viewpag=view.findViewById(R.id.home_viewpager)

        var listFragment: MutableList<Pair<String, Fragment>> = mutableListOf("首页" to HomeTab1(), "功率查询" to HomeTab2())
        //关键在于先关联viewpager，后修改tab布局（注意在绑定了viewpager后tablayout的tab已经被设置了，所以做修改操作就好了）
        home_viewpag.adapter = TabPagerAdapter(childFragmentManager, listFragment)
      //  tabLayout.setupWithViewPager(home_viewpag)
        home_viewpag.currentItem = 0
//        listFragment.forEachIndexed { i, pair ->
//            //设置自定义显示小红点的布局
//            var tab = tabLayout.getTabAt(i)
//            tab?.setCustomView(R.layout.tablayoutview)
//            var tabtext:TextView = tab?.customView?.findViewById<TextView>(R.id.tabtext)!!
//            tabtext?.text = pair.first
//        }

//        val view1: View? = tabLayout.getTabAt(0)?.getCustomView()
//        val view2: View? = tabLayout.getTabAt(1)?.getCustomView()
//        var text1:TextView = view1?.findViewById(R.id.tabtext)!!
//        var text2:TextView = view2?.findViewById(R.id.tabtext)!!
//        text1?.setTextSize(20f)
//        text2?.setTextSize(18f)
//        val paint: TextPaint = text1.getPaint()
//        paint.isFakeBoldText = true
//          tabLayout.addOnTabSelectedListener(object :OnTabSelectedListener{
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//
//
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                val view: View? = tab?.getCustomView()
//                tabtext = view?.findViewById(R.id.tabtext)!!
//                tabtext?.setTextSize(18f)
//                val paint: TextPaint = tabtext.getPaint()
//                paint.isFakeBoldText = false
//            }
//
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                val view: View? = tab?.getCustomView()
//                tabtext = view?.findViewById(R.id.tabtext)!!
//                tabtext?.setTextSize(20f)
//                val paint: TextPaint = tabtext.getPaint()
//                paint.isFakeBoldText = true
//            }
//        })
//        home_viewpag.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrollStateChanged(state: Int) {
//                var view: View? =tabLayout.getTabAt(state)?.customView
//                tabtext.setTextSize(20f)
//                tabtext.setTextColor(Color.BLUE)
//            }
//
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                var view: View? =tabLayout.getTabAt(position)?.customView
//                tabtext.setTextSize(24f)
//                tabtext.setTextColor(Color.BLUE)
//            }
//
//            override fun onPageSelected(position: Int) {
//
//            }
//        })

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
    class TabPagerAdapter<T : Fragment>(fm: FragmentManager, list: MutableList<Pair<String, T>>):FragmentPagerAdapter(fm) {
        private var flist = list
        override fun getItem(p0: Int): Fragment {
            return flist[p0].second
        }

        override fun getCount() = flist.size
    }
}
