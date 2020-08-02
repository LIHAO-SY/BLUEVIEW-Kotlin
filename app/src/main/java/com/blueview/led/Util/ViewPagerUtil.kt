package com.blueview.led.Util

import androidx.viewpager.widget.ViewPager

open class ViewPagerUtil {

    companion object test
    {
        open lateinit var a:ViewPager
        fun setview(viewp:ViewPager)
        {

        }
        fun getview():ViewPager
        {
            return a
        }
    }
    private lateinit  var mviewPager: ViewPager

    fun setViewPager(viewPager:ViewPager)
    {
        mviewPager=viewPager
    }
    fun getViewPager():ViewPager
    {
        return mviewPager
    }
}