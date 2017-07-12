package com.thinkgem.jeesite.modules.util;

import java.util.Date;

/**
 * Created by boxiaotong on 2017/1/17.
 */
public class DateUtil {

    public static float getHourDiff(Date start,Date end){
        long nh = 1000 * 60 * 60;
        long diff = end.getTime() -start .getTime();
        // 计算差多少天
        float hour = diff / nh;
        return hour;
    }

}
