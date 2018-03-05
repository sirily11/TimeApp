package com.example.qiweili.timeapp.city

import android.content.Context
import com.example.qiweili.timeapp.timer.listCities
import com.google.gson.GsonBuilder
import java.io.InputStream

/**
 * Created by qiweili on 2018/3/5.
 */
class FileReader(fileName : String,context : Context){

    private var inputStream : InputStream? = null
    private var inputString : String? = null
    init {
        inputStream = context.assets.open(fileName)
        inputString = inputStream?.bufferedReader().use{ it?.readText()}
        println(inputString?.get(2))
    }

    fun getListCitiesObject() : List<CityObject>{
        val gson = GsonBuilder().create()
        val citiesObject : List<CityObject> = gson.fromJson(inputString,Array<CityObject>::class.java).toList()
        return citiesObject
    }


}
class CityObject(val id : Int, val name : String){


}