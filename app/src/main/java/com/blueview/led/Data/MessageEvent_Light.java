package com.blueview.led.Data;

public class MessageEvent_Light
{
   private String msg;
   public MessageEvent_Light(String msg)
   {
      this.msg=msg;
   }
   public String getLight()
   {
      return msg;
   }
}
