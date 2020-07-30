package com.blueview.led.ui.Text_Bottom

import android.annotation.SuppressLint
import android.app.usage.UsageEvents
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast


class TextBottom @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr)
{
    private var  paint:Paint= Paint()
    private var mcanvas:Canvas= Canvas()
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        mcanvas=canvas!!
        paint.isAntiAlias=true
        paint.textSize= 50F
        paint.setShadowLayer(10f ,13f, 13f, Color.DKGRAY)
        var rect:Rect= Rect()
        rect.set(0,height-10,200,height)
        mcanvas?.drawRect(rect,paint)
        mcanvas?.drawText("height:"+height+"\nwidth:"+width,0f,52f,paint)


        canvas?.save()

        super.onDraw(canvas)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action)
        {
            MotionEvent.ACTION_DOWN->
            {
                print("dsggdsgd")
                Toast.makeText(context,"test",Toast.LENGTH_SHORT)
                return true
            }
            MotionEvent.ACTION_POINTER_DOWN->
                Toast.makeText(context,"test",Toast.LENGTH_SHORT)
        }
        return super.onTouchEvent(event)
    }
}



