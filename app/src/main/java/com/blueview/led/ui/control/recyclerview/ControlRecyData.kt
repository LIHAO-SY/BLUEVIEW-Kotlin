package com.blueview.led.ui.control.recyclerview

class ControlRecyData(var str1:String,var str2:String,var str3:String,var str4:String)
{
    private  var mstr1:String=str1
    private  var mstr2:String=str2
    private  var mstr3:String=str3
    private  var mstr4:String=str4

    fun getstr1(): String {
       return mstr1
    }
    fun getstr2(): String {
        return mstr2
    }
    fun getstr3(): String {
        return mstr3
    }
    fun getstr4(): String {
        return mstr4
    }
}