package com.blueview.led.UI.ViewPager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoScrollViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    private  var isscrool:Boolean=false

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if(isscrool)
        {
            return super.onInterceptTouchEvent(ev)
        }else{
            return false
        }

    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {

        if(isscrool)
        {
            return super.onTouchEvent(ev)

        }else{
            return true
        }
    }
    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, false)
    }

    fun setScroll(scroll: Boolean) {
        isscrool = scroll
    }
}