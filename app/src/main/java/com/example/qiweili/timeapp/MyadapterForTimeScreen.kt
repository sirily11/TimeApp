package com.example.qiweili.timeapp

import android.annotation.SuppressLint
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.qiweili.timeapp.timer.City
import com.example.qiweili.timeapp.timer.clocker
import kotlinx.android.synthetic.main.cardview.view.*

/**
 * Created by qiweili on 2018/2/18.
 */
class MyadapterForTimeScreen(time: List<clocker>, cities: List<City>?) : RecyclerView.Adapter<TimeScreen.ViewHolder>() {

    val time = time
    val cities = cities

    override fun getItemCount(): Int {
        return time.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TimeScreen.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.cardview, parent, false)
        return TimeScreen.ViewHolder(cellForRow)
    }

    //holder?.view?.cardview?.sun_or_moon?.setBackgroundResource(R.drawable.rain)
    //cities?.get(position)?.weather?.get(0)?.returnWeather() == "Sun"
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TimeScreen.ViewHolder?, position: Int) {
        val currentTime = time[position]
        val currentWeather = cities?.get(position)?.weather?.get(0)?.returnWeather()
        val currentTemp = cities?.get(position)?.main?.getMinHighTemp()
        var weatherIcon = R.drawable.sun
        val currentData = time.get(position).date



        /**
         * Setting the country
         */

        holder?.view?.cardview?.timezone?.text = cities?.get(position)?.name + currentTemp
        holder?.view?.cardview?.date?.text = currentData.toString()

        /**
         * Setting the clock
         */

        holder?.view?.cardview?.time?.text = currentTime.time
        if(!currentTime.isSunrise()){
            weatherIcon = R.drawable.sun
            holder?.view?.cardview?.cardview_background?.setBackgroundResource(R.drawable.daylight3)

        }else{
            holder?.view?.cardview?.cardview_background?.setBackgroundResource(R.drawable.daylight3)
            weatherIcon = R.drawable.moon

        }

        /**
         * setting the weather
         */
        if(currentWeather == "Sun") {
            //holder?.view?.cardview?.sun_or_moon?.setBackgroundResource(weatherIcon)
        }else{
            //weatherIcon = R.drawable.rain
            //holder?.view?.cardview?.sun_or_moon?.setBackgroundResource(weatherIcon)
        }




    }
}