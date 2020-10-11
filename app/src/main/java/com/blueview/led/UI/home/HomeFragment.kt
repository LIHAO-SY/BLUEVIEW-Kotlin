package com.blueview.led.UI.home

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
import com.blueview.led.AiControl.TtsControl
import com.blueview.led.R
import com.blueview.led.Data.MessageEvent_Aimsg
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var tabLayout: TabLayout
    open lateinit var home_viewpag: ViewPager
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
        //activity?.let { changeStatusBarTransparent(it) }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayout = view.findViewById(R.id.home_tab_title)
        home_viewpag=view.findViewById(R.id.home_viewpager)
        EventBus.getDefault().register(this)
        var listFragment: MutableList<Pair<String, Fragment>> = mutableListOf("首页" to HomeTab1(), "数据统计" to HomeTab2())
        //关键在于先关联viewpager，后修改tab布局（注意在绑定了viewpager后tablayout的tab已经被设置了，所以做修改操作就好了）
        home_viewpag.adapter = TabPagerAdapter(childFragmentManager, listFragment)
        home_viewpag.currentItem =0
        tabLayout.setupWithViewPager(home_viewpag)
        listFragment.forEachIndexed { i, pair ->
            //设置自定义显示小红点的布局
            tabLayout.addTab(tabLayout.newTab())
            var tab: TabLayout.Tab? = tabLayout.getTabAt(i)
            tab?.setCustomView(R.layout.tablayoutview)
            var tabtext:TextView = tab?.customView?.findViewById(R.id.tabtext)!!
            tabtext?.text = pair.first
        }

        val view1: View? = tabLayout.getTabAt(0)?.getCustomView()
        val view2: View? = tabLayout.getTabAt(1)?.getCustomView()
        var text1:TextView = view1?.findViewById(R.id.tabtext)!!
        var text2:TextView = view2?.findViewById(R.id.tabtext)!!
        text1?.setTextSize(20f)
        text2?.setTextSize(18f)
        val paint: TextPaint = text1.getPaint()
        paint.isFakeBoldText = true
          tabLayout.addOnTabSelectedListener(object :OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {


            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val view: View? = tab?.getCustomView()
                tabtext = view?.findViewById(R.id.tabtext)!!
                tabtext?.setTextSize(18f)
                val paint: TextPaint = tabtext.getPaint()
                paint.isFakeBoldText = false
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val view: View? = tab?.getCustomView()
                tabtext = view?.findViewById(R.id.tabtext)!!
                tabtext?.setTextSize(20f)
                val paint: TextPaint = tabtext.getPaint()
                paint.isFakeBoldText = true
            }
        })
        home_viewpag.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {


            }

            override fun onPageSelected(position: Int) {
                var view: View? =tabLayout.getTabAt(position)?.customView
                tabtext = view?.findViewById(R.id.tabtext)!!
                tabtext.setTextSize(20f)
            }
        })

    }

    class TabPagerAdapter<T : Fragment>(fm: FragmentManager, list: MutableList<Pair<String, T>>):FragmentPagerAdapter(fm) {
        private var flist = list
        override fun getItem(p0: Int): Fragment {
            return flist[p0].second
        }

        override fun getCount() = flist.size
    }

    @Subscribe(threadMode = ThreadMode.MAIN)//这个是最重要的函数，用来处理接收到的数据，@Subscribe是用来标记线程模式。
    fun MessageEvent(event: MessageEvent_Aimsg)
    {
        if(event.aimsg.indexOf("查询")>=0)
        {
            home_viewpag.setCurrentItem(1)
            context?.let { TtsControl.TtsControlinit(context = it, string = "已打开查询") }
        }
        if(event.aimsg.indexOf("首页")>=0)
        {
            home_viewpag.setCurrentItem(0)
            context?.let { TtsControl.TtsControlinit(context = it, string = "已打开首页") }
        }
    }
}
