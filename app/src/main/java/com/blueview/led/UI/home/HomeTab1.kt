package com.blueview.led.UI.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.blueview.led.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.*


class HomeTab1 : Fragment()
{
    private lateinit var lineChart: LineChart
    private lateinit var entries:ArrayList<Entry>
    private lateinit var dataSet: LineDataSet
    private lateinit var lineData: LineData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view:View=inflater.inflate(R.layout.home_tab1, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lineChart=view.findViewById(R.id.LineChart)
        entries=ArrayList<Entry>()
        val ran:Random = Random(System.currentTimeMillis())

        for (i in 1..20)
        {
            var b=ran.nextInt(20)
            if (b>0)
            {
                entries.add(Entry(i.toFloat(),b.toFloat()))
            }

        }

        dataSet= LineDataSet(entries,"test")
        dataSet.color=Color.RED
        //dataSet.setValueTextColors(Color.RED)
        lineData= LineData(dataSet)
        lineChart.data=lineData
        lineChart.invalidate()
    }


}
