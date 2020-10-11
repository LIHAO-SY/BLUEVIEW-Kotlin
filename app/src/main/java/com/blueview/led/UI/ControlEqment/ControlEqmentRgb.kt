package com.blueview.led.UI.ControlEqment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.blueview.led.Data.MessageEvent_Rgb
import com.blueview.led.R
import com.blueview.led.colorpicker.ColorPickerView
import org.greenrobot.eventbus.EventBus
import java.util.*

class ControlEqmentRgb: Fragment()
{
    private lateinit var colorPickerView:ColorPickerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.ledrgbcontrol, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        colorPickerView=view.findViewById(R.id.colorPickerView)
        colorPickerView.setInitialColor(Color.parseColor("#FFFFFF"))
        colorPickerView.subscribe { color, fromUser, shouldPropagate ->
            Log.e("colorHex","colorHex:"+colorHex(color))
            EventBus.getDefault().post(MessageEvent_Rgb("#"+colorHex(color)))
            //Toast.makeText(context,color.toString(),Toast.LENGTH_LONG).show()
        }
    }
    private fun colorHex(color: Int): String? {
        val a: Int = Color.alpha(color)
        val r: Int = Color.red(color)
        val g: Int = Color.green(color)
        val b: Int = Color.blue(color)
        return java.lang.String.format(Locale.getDefault(), "%02X%02X%02X%02X", a, r, g, b)
    }
}