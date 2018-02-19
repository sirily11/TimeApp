package com.example.qiweili.timeapp

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



class TimeScreen : AppCompatActivity() {
    val clocker = clocker()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_screen)

        countries.layoutManager = LinearLayoutManager(countries.context)
        countries.adapter = MyadapterForTimeScreen(clocker)
        updateTime()
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



    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){

    }
}
