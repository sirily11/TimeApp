package com.example.qiweili.timeapp.city

import android.content.Context
import android.widget.ArrayAdapter

/**
 * Created by qiweili on 2018/3/5.
 */
class CityList(val context : Context,val resource :Int) {

    var fileReader : FileReader? = null
    var cities = mutableListOf<String>()
    var citiesMap = mutableMapOf<String,Int>()

    init {
        fileReader = FileReader("cityList.json",context)
        val citiesObject = fileReader?.getListCitiesObject()
        if (citiesObject != null) {
            for(i in citiesObject){
                cities.add(i.name)
                citiesMap.put(i.name,i.id)
            }
        }

    }

    fun getArrayAdapter(): ArrayAdapter<String> {
        val arrayAdapter =  ArrayAdapter<String>(context,resource,cities.toTypedArray())
        return arrayAdapter
    }

    fun getID(name : String) : Int?{

        return citiesMap.get(name)
    }

}