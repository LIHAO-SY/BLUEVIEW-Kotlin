package com.blueview.led.UI.ControlEqment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.blueview.led.R
import com.blueview.led.Data.MessageEvent_Light
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ControlEqmentLight:Fragment() {

    private lateinit var lt_or_eq:TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.ledlightcontrol, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
        lt_or_eq = view.findViewById(R.id.textview_lt_or_eq)
    }
    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)//反注册订阅者，在onDestory中反注册
    }

    @Subscribe(threadMode = ThreadMode.MAIN)//这个是最重要的函数，用来处理接收到的数据，@Subscribe是用来标记线程模式。
    fun onMessageEvent(eventLight: MessageEvent_Light){//线程具体内容可自行查找，大部分的都用ThreadMode.MAIN，应为更新UI都是在主线程
        lt_or_eq.text = eventLight.light//给组件TextView赋值
    }

}


