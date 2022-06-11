package com.amirreza.domain.entity;

import java.util.Calendar;

public class TimeProcess {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int dayOfWeek;

    public TimeProcess(long utc){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(utc * 1000);

        this.year = calendar.get(Calendar.YEAR);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(Calendar.MINUTE);
        this.month = calendar.get(Calendar.MONTH);
        this.dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    }

    public long getYear() {
        return year;
    }

    public long getMonth() {
        return month;
    }

    public long getDay() {
        return day;
    }

    public long getHour() {
        return hour;
    }

    public long getMinute() {
        return minute;
    }

    public String getMonthName(){
        String[] monthName = {"January","February","March","April","May","June","July","August",
                                "September", "October", "November" ,"December"};
        return monthName[month];
    }

    public String getDateIn_MONTH_DAY_YEAR_format(){
        String monthName = getMonthName();
        return monthName + " " + day + ", " + year;
    }

    public String getDateIn_MONTH_DAY_DAYOFWEEK_format(){
        String[] week = {"Sat","Sun","Mon","Tue","Wed","Thu","Fri"};
        String monthName = getMonthName();
        return monthName + " " + day + ", " + week[dayOfWeek%7];
    }

}
