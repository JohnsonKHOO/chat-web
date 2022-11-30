package com.a2208.chat.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//对日期进行处理
public class DateUtil {

    public static Date dateParse(String birthday) throws ParseException {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date d = fmt.parse(birthday);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        //日期加一天
        c.add(Calendar.DAY_OF_MONTH,2);
        return c.getTime();
    }

}