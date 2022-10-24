package com.a2208.chat.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date dateParse(String birthday) throws ParseException {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = fmt.parse(birthday);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        //日期加一天
        c.add(Calendar.DAY_OF_MONTH,1);
        return c.getTime();
    }

}