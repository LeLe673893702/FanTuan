package com.anonymouser.book.utlis;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 17173
 * @what
 * @date 2018/11/22
 */
public class DateUtil {
    public static String stampToDate(long time){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }
}
