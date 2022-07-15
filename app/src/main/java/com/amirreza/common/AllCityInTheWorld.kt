package com.amirreza.common

import android.content.Context
import com.amirreza.domain.entity.CityEntity
import com.amirreza.weatherapplication.R
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.util.*

class AllCityInTheWorld(val context: Context) {
    lateinit var allCitiesInTheWorld: MutableList<String>
    fun getCityCoordinators(name: String): CityEntity? {
        for (i in allCitiesInTheWorld!!.indices) {
            val city = allCitiesInTheWorld[i]
            val cityData = allCitiesInTheWorld[i].split("!").toTypedArray()
            if (cityData[0] == name && cityData[0].length == name.length) {
                return CityEntity(cityData[2], cityData[3])
            }
        }
        return null
    }

    init {
        fetchDataFromFile()
    }
    private fun fetchDataFromFile(){
        try {
            allCitiesInTheWorld = mutableListOf()
            val cityInput = context.resources.openRawResource(R.raw.all)
            val cityReader = BufferedReader(InputStreamReader(cityInput))
            var cityLine: String
            while (cityReader.readLine().also { cityLine = it } != null) {
                allCitiesInTheWorld.add(cityLine)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
