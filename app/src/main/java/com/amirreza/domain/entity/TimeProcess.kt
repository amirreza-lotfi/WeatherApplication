package com.amirreza.domain.entity

import java.util.*

class TimeProcess(utc: Long) {
    private val year: Int
    private val month: Int
    private val day: Int
    private val hour: Int
    private val minute: Int
    private val dayOfWeek: Int

    fun getHour(): Long {
        return hour.toLong()
    }

    fun getMinute(): Long {
        return minute.toLong()
    }

    val monthName: String
        get() {
            val monthName = arrayOf(
                "January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December"
            )
            return monthName[month]
        }
    val dateIn_MONTH_DAY_YEAR_format: String
        get() {
            val monthName = monthName
            return "$monthName $day, $year"
        }
    val dateIn_MONTH_DAY_DAYOFWEEK_format: String
        get() {
            val week = arrayOf("Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri")
            val monthName = monthName
            return monthName + " " + day + ", " + week[dayOfWeek % 7]
        }

    init {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = utc * 1000
        year = calendar[Calendar.YEAR]
        day = calendar[Calendar.DAY_OF_MONTH]
        hour = calendar[Calendar.HOUR_OF_DAY]
        minute = calendar[Calendar.MINUTE]
        month = calendar[Calendar.MONTH]
        dayOfWeek = calendar[Calendar.DAY_OF_WEEK]
    }
}