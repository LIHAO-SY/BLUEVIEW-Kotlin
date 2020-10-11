package com.blueview.led.UI.map

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.baidu.mapapi.map.*
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener
import com.baidu.mapapi.model.LatLng
import com.blueview.led.R


class MapFragment : Fragment() {

    private lateinit var notificationsViewModel: MapViewModel
    private lateinit var mapview:MapView
    private lateinit var baiduMap: BaiduMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =ViewModelProvider(this)[MapViewModel::class.java]
        val root = inflater.inflate(R.layout.fragment_map, container, false)
        return root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapview=view.findViewById(R.id.MapView)
        baiduMap=mapview.map
        //定义Maker坐标点

        //定义Maker坐标点
        val point = LatLng(39.944251, 116.494996)
//构建Marker图标
//构建Marker图标
        val bitmap = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_mark1)
//构建MarkerOption，用于在地图上添加Marker
//构建MarkerOption，用于在地图上添加Marker
        val option: OverlayOptions = MarkerOptions()
            .animateType(MarkerOptions.MarkerAnimateType.grow)
            .position(point) //必传参数
            .icon(bitmap) //必传参数
            .draggable(false) //设置平贴地图，在地图中双指下拉查看效果
            .flat(true)
            .alpha(0.9f)
        val mapstate:MapStatus=MapStatus.Builder()
            .target(point)
            .zoom(16f)
            .build()
        val mapStatusUpdate:MapStatusUpdate=MapStatusUpdateFactory.newMapStatus(mapstate)
        baiduMap.setMapStatus(mapStatusUpdate)
        baiduMap.addOverlay(option)
        baiduMap.setOnMarkerClickListener(OnMarkerClickListener {

            Toast.makeText(context,"OnMarkerClickListener",Toast.LENGTH_SHORT).show()
            false
        })
    }
    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapview.onResume()
    }

     override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
         mapview.onPause()
    }

     override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
         mapview.onDestroy()
    }
}


