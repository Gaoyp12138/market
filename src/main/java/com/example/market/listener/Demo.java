package com.example.market.listener;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author gaoyp
 * @create 2018/7/9  14:55
 **/
public class Demo {

     public static int time = 60 * 60 * 60;
     public static Calendar c;
     public static long endTime;
     public static Date date;
     public static long startTime;
     public static long midTime;

    public static void main(String[] args){
                 c = Calendar.getInstance();
                 c.set(2017, 4, 17, 0, 0, 0);// 注意月份的设置，0-11表示1-12月
                 endTime = c.getTimeInMillis();
                 date = new Date();
                 startTime = date.getTime();
                 midTime = (endTime - startTime) / 1000;
                 new Demo().time2();
    }

    public void time2() {

        while (midTime > 0) {
            midTime--;
            long hh = midTime / 60 / 60 % 60;
            long mm = midTime / 60 % 60;
            long ss = midTime % 60;
            System.out.println("还剩" + hh + "小时" + mm + "分钟" + ss + "秒");
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
