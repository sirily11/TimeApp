package com.example.qiweili.timeapp.timer

import android.provider.ContactsContract
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Create a clocker object which will have a sunrise and set's time
 * It has a update method which can let the object get the latest
 * time.
 */
class clocker(val timezone : String, private val sunrise: Int?, private val sunset: Int?) {
    private var country: TimeZone = TimeZone.getTimeZone(timezone)
    private var countryInstance = Calendar.getInstance(country)

    var date = countryInstance.get(Calendar.DAY_OF_MONTH)
    var time = "${countryInstance.get(Calendar.HOUR_OF_DAY)} : ${countryInstance.get(Calendar.MINUTE)} : ${countryInstance.get(Calendar.SECOND)}"

    fun update() {
        date = countryInstance.get(Calendar.DAY_OF_MONTH)
        country = TimeZone.getTimeZone(timezone)
        countryInstance = Calendar.getInstance(country)
        time = "${countryInstance.get(Calendar.HOUR_OF_DAY)} : ${countryInstance.get(Calendar.MINUTE)} : ${countryInstance.get(Calendar.SECOND)}"
    }

    fun isSunrise(): Boolean {
        val sunriseTime : Date = if(sunrise != null) Date(sunrise.toLong()) else Date(6)
        val sunsetTime : Date = if(sunset != null) Date(sunset.toLong()) else Date(18)
        if(countryInstance.get(Calendar.HOUR_OF_DAY) > 6 &&  countryInstance.get(Calendar.HOUR_OF_DAY) < 18){
            return false
        }
        return true
    }

}