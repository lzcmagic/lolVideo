package com.lzc.exovideo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

    private SimpleDateFormat sdf;
    private static TimeUtil instance;
    private TimeUtil(){

        sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }
    public static TimeUtil getInstance(){
        if (instance==null){
            instance =new TimeUtil();
        }
        return instance;
    }

    public Date fotmatTimeByString(String time) throws ParseException {
        return sdf.parse(time);
    }

    public String formatDateToString(Date date){
        return sdf.format(date);
    }
}
