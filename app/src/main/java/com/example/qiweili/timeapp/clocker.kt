package com.example.qiweili.timeapp

import java.util.*

/**
 * Created by qiweili on 2018/2/18.
 */
class clocker {
    private var tz_china: TimeZone = TimeZone.getTimeZone("GMT+08:00")
    private var tz_chicago: TimeZone = TimeZone.getTimeZone("GMT-06:00")
    private var c_chicago = Calendar.getInstance(tz_chicago)
    private var c_china = Calendar.getInstance(tz_china)

    var time_china = "${c_china.get(Calendar.HOUR_OF_DAY)} : ${c_china.get(Calendar.MINUTE)} : ${c_china.get(Calendar.SECOND)}"
    var time_chicago = "${c_chicago.get(Calendar.HOUR_OF_DAY)} : ${c_chicago.get(Calendar.MINUTE)} : ${c_chicago.get(Calendar.SECOND)}"
    var china_daytime =  6 <c_china.get(Calendar.HOUR_OF_DAY) && c_china.get(Calendar.HOUR_OF_DAY) < 18
    var chicago_daytime =  6 <c_chicago.get(Calendar.HOUR_OF_DAY) && c_chicago.get(Calendar.HOUR_OF_DAY) < 18

    fun update(){
        tz_china = TimeZone.getTimeZone("GMT+08:00")
        tz_chicago = TimeZone.getTimeZone("GMT-06:00")
        c_chicago = Calendar.getInstance(tz_chicago)
        c_china = Calendar.getInstance(tz_china)
        time_china = "${c_china.get(Calendar.HOUR_OF_DAY)} : ${c_china.get(Calendar.MINUTE)} : ${c_china.get(Calendar.SECOND)}"
        time_chicago = "${c_chicago.get(Calendar.HOUR_OF_DAY)} : ${c_chicago.get(Calendar.MINUTE)} : ${c_chicago.get(Calendar.SECOND)}"
        china_daytime =  6 <c_china.get(Calendar.HOUR_OF_DAY) && c_china.get(Calendar.HOUR_OF_DAY) < 18
        chicago_daytime =  6 <c_chicago.get(Calendar.HOUR_OF_DAY) && c_chicago.get(Calendar.HOUR_OF_DAY) < 18
    }
}