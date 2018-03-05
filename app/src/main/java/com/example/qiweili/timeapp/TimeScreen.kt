package com.example.qiweili.timeapp

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.Toast
import com.example.qiweili.timeapp.city.CityList
import com.example.qiweili.timeapp.city.FileReader
import com.example.qiweili.timeapp.timer.City
import com.example.qiweili.timeapp.timer.clocker
import com.example.qiweili.timeapp.timer.listCities
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_time_screen.*
import kotlinx.android.synthetic.main.popup.view.*
import okhttp3.*
import java.io.IOException


class TimeScreen : AppCompatActivity() {
    var mclocks = mutableListOf<clocker>()
    var mCities = mutableListOf<City>()
    var mcityCode : Int? = null
    val shenzhen = 1795565
    val changzhi = 1815463
    val ames = 4846834

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_screen)

        setSupportActionBar(tool_bar)
        //supportActionBar?.setTitle("Hello world")
        countries.layoutManager = LinearLayoutManager(countries.context)
        countries.adapter = MyadapterForTimeScreen(mclocks, mCities)
        runOnUiThread {
            /**
             * Default city
             */
            getWeather(this, changzhi, "GMT+08:00", null, null)
            getWeather(this, ames, "GMT-06:00", null, null)
            /**
             * Added new city
             */
            if (mcityCode != null) {
                getWeather(this, mcityCode!!, "GMT+08:00", null, null)
            }
            updateTime()
        }

    }

    fun updateTime() {
        val timerHandler = Handler()
        var updater: Runnable? = null
        updater = Runnable {
            for (clock in mclocks) {
                clock.update()
                countries.adapter.notifyDataSetChanged()
            }
            timerHandler.postDelayed(updater, 1000)
        }
        timerHandler.post(updater)
    }

    fun getWeather(context: Context,id : Int,timeZone : String, sunrise : Int?, sunset : Int?) {
        var apiwebsite = "http://api.openweathermap.org/data/2.5/group?id="
        val apikey = "&APPID=f0fd2b714837ffbd8fe592111884247f"
        val client = OkHttpClient()
        val request = Request.Builder().url(apiwebsite + id + apikey).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                println("Error")
                if(!mclocks.contains(clocker(timeZone, sunrise, sunset))) {
                    mclocks.add(clocker(timeZone, null, null))
                }
            }

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val list = gson.fromJson(body, listCities::class.java)
                if(!mclocks.contains(clocker(timeZone, sunrise, sunset))) {
                    mclocks.add(clocker(timeZone, sunrise, sunset))
                }
                if(!mCities.contains(list.list[0])){
                mCities.add(list.list[0])
                }
            }
        })

    }

    fun showCityChoices() : String?{
        val mBuilder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.popup, null)
        var cityname: String? = null

        /**
         * Create a city list object
         */
        val cities = CityList(this, R.layout.autocomplete_layout)
        mBuilder.setView(view)
        val dialog = mBuilder.create()

        /**
         * Set the auto complete for the input text
         */
        view.cityName_text.threshold = 2
        view.cityName_text.setAdapter(cities.getArrayAdapter())

        /**
         * Set the button action
         */
        view.cancel.setOnClickListener {
            dialog.dismiss()
        }

        view.set.setOnClickListener {
            cityname = view.cityName_text.text.toString()
            if(cityname != null) {
                Log.i("ID",cities.getID(cityname!!).toString())
                Toast.makeText(this, "ID of $cityname is ${cities.getID(cityname!!)}", Toast.LENGTH_SHORT)
                    .show()
                mcityCode = cities.getID(cityname!!)
                runOnUiThread {
                    getWeather(this, mcityCode!!, "GMT+08:00", null, null)
                    updateTime()
                }
            }
            dialog.dismiss()
        }
        dialog.show()


        return cityname

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val mMenuInflater = menuInflater
        mMenuInflater.inflate(R.menu.settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.add -> {
            showCityChoices()
            true
        }
        else -> {
            true
        }
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }
}
