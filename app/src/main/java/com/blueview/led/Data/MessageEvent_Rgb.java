package com.blueview.led.Data;

public class MessageEvent_Rgb {
    private String rgb;
    public MessageEvent_Rgb(String msg)
    {
        this.rgb=msg;
    }
    public String getRgb()
    {
        return rgb;
    }
}
