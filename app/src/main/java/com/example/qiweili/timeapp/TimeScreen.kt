package com.example.qiweili.timeapp

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.qiweili.timeapp.city.CityList
import com.example.qiweili.timeapp.timer.City
import com.example.qiweili.timeapp.timer.clocker
import com.example.qiweili.timeapp.timer.listCities
import com.example.qiweili.timeapp.util.DatabaseHelper
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_time_screen.*
import kotlinx.android.synthetic.main.popup.view.*
import okhttp3.*
import java.io.IOException


class TimeScreen : AppCompatActivity() {
    var mclocks = mutableListOf<clocker>()
    var mCities = mutableListOf<City>()
    var myDB: DatabaseHelper? = null
    var mcityCode: Int? = null
    var mCityName: String? = null
    val shenzhen = 1795565
    val changzhi = 1815463
    val ames = 4846834

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_screen)
        setSupportActionBar(tool_bar)
        //supportActionBar?.setTitle("Hello world")
        countries.layoutManager = LinearLayoutManager(countries.context)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        myDB = DatabaseHelper(this, "TimeApp.db")

        runOnUiThread {
            /**
             * Default city
             */
            getWeather(this, changzhi, "Changzhi", "GMT+08:00", null, null)
            getWeather(this, ames, "Ames", "GMT-06:00", null, null)
            try {
                var count = 0
                val temp = myDB?.getClockers()
                for (i in myDB?.getCities()!!) {
                    getWeather(this, i.get(1).toIntOrNull()!!, i[0], temp?.get(count)?.toString()!!, null, null)
                    count++
                }

            } catch (e: Exception) {
                Log.e("Database", e.toString())
            }


            countries.adapter = MyadapterForTimeScreen(mclocks, mCities)
            /**
             * Added new city
             */
            if (mcityCode != null) {
                //getWeather(this, mcityCode!!,mCityName!!, "GMT+08:00", null, null)
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

    fun getWeather(context: Context, id: Int, name: String, timeZone: String, sunrise: Int?, sunset: Int?) {
        var apiwebsite = "http://api.openweathermap.org/data/2.5/group?id="
        val apikey = "&APPID=f0fd2b714837ffbd8fe592111884247f"
        val client = OkHttpClient()
        val request = Request.Builder().url(apiwebsite + id + apikey).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                println("Error")
                if (!mclocks.contains(clocker(timeZone, sunrise, sunset))) {
                    mclocks.add(clocker(timeZone, null, null))
                    mCities.add(City(null, null, null, name))
                }
            }

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val list = gson.fromJson(body, listCities::class.java)
                if (!mclocks.contains(clocker(timeZone, sunrise, sunset))) {
                    mclocks.add(clocker(timeZone, sunrise, sunset))
                }
                if (!mCities.contains(list.list[0])) {
                    mCities.add(list.list[0])
                }
            }
        })

    }

    fun showCityChoices(): String? {
        val mBuilder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.popup, null)

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
            mCityName = view.cityName_text.text.toString()
            if (mCityName != null) {
                Log.i("ID", cities.getID(mCityName!!).toString())
                mcityCode = cities.getID(mCityName!!)
                runOnUiThread {
                    getWeather(this, mcityCode!!, mCityName!!, "GMT+08:00", null, null)
                    val success = myDB?.insertData(mCityName!!, "GMT+08:00", mcityCode!!)
                    Toast.makeText(this, "Insert is success is $success", Toast.LENGTH_SHORT).show()
                    updateTime()
                }
            }
            dialog.dismiss()
        }
        dialog.show()


        return mCityName

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
