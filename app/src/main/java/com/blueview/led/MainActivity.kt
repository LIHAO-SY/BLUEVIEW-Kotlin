package com.blueview.led

import android.os.Bundle
import android.renderscript.Allocation
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.blueview.led.ui.control.ControlFragment
import com.blueview.led.ui.home.HomeFragment
import com.blueview.led.ui.map.MapFragment
import com.blueview.led.ui.user.UserFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var menuItem: MenuItem
    private lateinit var fragmentPagerAdapter: FragmentPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        viewPager=findViewById(R.id.main_viewpager)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        fragmentPagerAdapter= MyAdapter(supportFragmentManager)
        viewPager.adapter=fragmentPagerAdapter
        navView.setOnNavigationItemSelectedListener(object :NavigationView.OnNavigationItemSelectedListener,
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                TODO("Not yet implemented")
                menuItem=item
                when(item.itemId)
                {
                    R.id.navigation_home->
                        viewPager.setCurrentItem(0,true)
                    R.id.navigation_control->
                        viewPager.setCurrentItem(1,true)
                    R.id.navigation_map->
                        viewPager.setCurrentItem(2,true)
                    R.id.navigation_user->
                        viewPager.setCurrentItem(3,true)
                }
            }
        })
        viewPager.addOnPageChangeListener(object:ViewPager.OnPageChangeListener{
           override fun onPageScrollStateChanged(state: Int) {
               TODO("Not yet implemented")
           }

           override fun onPageScrolled(
               position: Int,
               positionOffset: Float,
               positionOffsetPixels: Int
           ) {
               if (menuItem!=null)
               {
                   menuItem.setChecked(false)
               }else{
                   navView.menu.getItem(0).setChecked(false)
               }
               menuItem=navView.menu.getItem(position).setChecked(true)

               TODO("Not yet implemented")
           }

           override fun onPageSelected(position: Int) {
               TODO("Not yet implemented")
           }
       })


    }
    class MyAdapter : FragmentPagerAdapter {
        var fragment_list: MutableList<Fragment> = ArrayList()

        constructor(fm: FragmentManager) : super(fm) {
            fragment_list.add(HomeFragment())
            fragment_list.add(ControlFragment())
            fragment_list.add(MapFragment())
            fragment_list.add(UserFragment())
        }

        override fun getItem(position: Int): Fragment {
            return fragment_list.get(position)
        }

        override fun getCount(): Int = fragment_list.size
    }
}







