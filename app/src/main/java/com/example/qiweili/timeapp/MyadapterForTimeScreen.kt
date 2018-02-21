package com.example.qiweili.timeapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.cardview.view.*

/**
 * Created by qiweili on 2018/2/18.
 */
class MyadapterForTimeScreen(time: clocker, cities: List<City>?) : RecyclerView.Adapter<TimeScreen.ViewHolder>() {

    val time = time
    val cities = cities
    override fun getItemCount(): Int {
        return 2;
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TimeScreen.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.cardview, parent, false)
        return TimeScreen.ViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: TimeScreen.ViewHolder?, position: Int) {
        val chicago = time.time_chicago
        val beijing = time.time_china
        if (position == 0) {
            holder?.view?.timezone?.text = "Ames ${cities?.get(position)?.main?.getMinHighTemp()}"
            holder?.view?.time?.text = chicago
            if (time.chicago_daytime) {
                holder?.view?.cardview_background?.setBackgroundResource(R.drawable.daylight2)
                if (cities?.get(position)?.weather?.get(0)?.returnWeather() == "Sun") {
                    holder?.view?.cardview?.sun_or_moon?.setBackgroundResource(R.drawable.sun)
                } else {
                    holder?.view?.cardview?.sun_or_moon?.setBackgroundResource(R.drawable.rain)
                }

            } else {
                holder?.view?.cardview_background?.setBackgroundResource(R.drawable.night2)
                if (cities?.get(position)?.weather?.get(0)?.returnWeather() == "Sun") {
                    holder?.view?.cardview?.sun_or_moon?.setBackgroundResource(R.drawable.moon)
                } else {
                    holder?.view?.cardview?.sun_or_moon?.setBackgroundResource(R.drawable.rain)
                }
            }
        } else {
            holder?.view?.timezone?.text = "Shenzhen ${cities?.get(position)?.main?.getMinHighTemp()}"
            holder?.view?.time?.text = beijing
            if (time.china_daytime) {
                holder?.view?.cardview_background?.setBackgroundResource(R.drawable.daylight1)
                try {
                    if (cities?.get(position)?.weather?.get(position)?.returnWeather() == "Sun") {
                        holder?.view?.cardview?.sun_or_moon?.setBackgroundResource(R.drawable.sun)
                    } else {
                        holder?.view?.cardview?.sun_or_moon?.setBackgroundResource(R.drawable.rain)
                    }
                } catch (e: Exception) {
                    println(e)
                }
            } else {
                holder?.view?.cardview_background?.setBackgroundResource(R.drawable.night1)
                try {
                    if (cities?.get(position)?.weather?.get(0)?.returnWeather() == "Sun") {
                        holder?.view?.cardview?.sun_or_moon?.setBackgroundResource(R.drawable.moon)
                    } else {
                        holder?.view?.cardview?.sun_or_moon?.setBackgroundResource(R.drawable.rain)
                    }
                } catch (e: Exception) {
                    println(e)
                }
            }

        }

    }
}