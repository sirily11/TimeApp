package com.example.qiweili.timeapp

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.activity_time_screen.*
import kotlinx.android.synthetic.main.activity_time_screen.view.*
import java.time.LocalTime
import android.widget.TextView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException


class TimeScreen : AppCompatActivity() {
    val clocker = clocker()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_screen)
        countries.layoutManager = LinearLayoutManager(countries.context)
        getWeather(this)

    }

    fun updateTime() {
        val timerHandler = Handler()
        var updater : Runnable? = null
        updater = Runnable {
            clocker.update()
            println(clocker.time_chicago)
            countries.adapter.notifyDataSetChanged()
            timerHandler.postDelayed(updater, 1000)
        }
        timerHandler.post(updater)
    }

    fun getWeather(context : Context){
        var apiwebsite = "http://api.openweathermap.org/data/2.5/group?id="
        val shenzhen = 1795565
        val ames = 4846834
        val apikey = "&APPID=f0fd2b714837ffbd8fe592111884247f"

        val client = OkHttpClient()
        val request = Request.Builder().url(apiwebsite + ames+ "," + shenzhen + apikey).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                println("Error")
                runOnUiThread {
                    countries.adapter = MyadapterForTimeScreen(clocker,null)
                    updateTime()
                }
            }

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val list = gson.fromJson(body, listCities::class.java)
                runOnUiThread {
                    countries.adapter = MyadapterForTimeScreen(clocker,list?.list)
                    updateTime()
                }
            }
        })


    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){

    }
}
