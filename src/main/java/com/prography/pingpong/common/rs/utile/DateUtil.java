package com.prography.pingpong.common.rs.utile;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String dateFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
