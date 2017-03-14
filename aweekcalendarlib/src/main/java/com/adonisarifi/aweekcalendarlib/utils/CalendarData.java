package com.adonisarifi.aweekcalendarlib.utils;

/**
 * Created by AArifi on 3/14/2017
 * Project:AweekCalendar
 * Email "adonisarifi@gmail.com"
 */

public class CalendarData {

    public int year;
    public int month;
    public int day;
    public int week = -1;
    public boolean isNextMonthDay = false;
    public boolean isLastMonthDay = false;

    public CalendarData() {
    }

    public CalendarData(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public boolean isSameDay(CalendarData data) {
        return (data.day == day && data.month == month &&data.year == year);
    }


    @Override
    public String toString() {
        return "CalendarData{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", week=" + week +
                ", isNextMonthDay=" + isNextMonthDay +
                ", isLastMonthDay=" + isLastMonthDay +
                '}';
    }
}
