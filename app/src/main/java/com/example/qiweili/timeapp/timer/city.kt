package com.example.qiweili.timeapp.timer

/**
 * Created by qiweili on 2018/2/21.
 */

class listCities(val cnt : Int, val list : List<City>){

}
class City(val sys: sys, val weather: List<Weather>, val main: main, val name : String) {
    val sunrise_set = sys
    val cityname = name


}

class sys(sunrise: Int, sunset: Int) {
    val sunrise = sunrise
    val sunset = sunset
}


class Weather(id: Int, main: String, description: String) {
    val main = main

    fun returnWeather(): String {
        val sunWeather = listOf("clear sky", "few clouds", "scattered clouds", "broken clouds")
        val rainWeather = listOf("shower rain", "rain", "thunderstorm", "snow", "mist")
        if (main in rainWeather) {
            return "Raining"
        }
        return "Sun"
    }

}

class main(temp: Double, temp_min: Double, temp_max: Double) {

    val temp_min = temp_min
    val temp_max = temp_max

    private fun ktoc(k: Double): String {
        val c = k - 273.15
        return "${c.toInt()}°C"
    }

    fun getMinHighTemp(): String {
        return "${ktoc(temp_min)} - ${ktoc(temp_max)} "
    }


}