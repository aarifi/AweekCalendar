package com.adonisarifi.aweekcalendarlib.utils;

import android.content.Context;

import com.adonisarifi.aweekcalendarlib.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AArifi on 3/14/2017
 * Project:AweekCalendar
 * Email "adonisarifi@gmail.com"
 */

public class Calendarutil {

    private static int daysOfMonth = 0;
    private static int dayOfWeek = 0;
    private static int eachDayOfWeek = 0;


    public static Map<Integer, String> getWeekString( Context mContext) {
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(1, mContext.getString(R.string.Monday));
        map.put(2, mContext.getString(R.string.Tuesday));
        map.put(3, mContext.getString(R.string.Wednesday));
        map.put(4, mContext.getString(R.string.Thursday));
        map.put(5, mContext.getString(R.string.Friday));
        map.put(6, mContext.getString(R.string.Saturday));
        map.put(0, mContext.getString(R.string.Sunday));
        return map;
    }

    public static boolean isLeapYear(int year) {
        if (year % 100 == 0 && year % 400 == 0) {
            return true;
        } else if (year % 100 != 0 && year % 4 == 0) {
            return true;
        }
        return false;
    }

    public static int getDaysOfMonth(CalendarData day) {
        switch (day.month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                daysOfMonth = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                daysOfMonth = 30;
                break;
            case 2:
                if (isLeapYear(day.year)) {
                    daysOfMonth = 29;
                } else {
                    daysOfMonth = 28;
                }

        }
        return daysOfMonth;
    }


    public static int getWeekdayOfFirstDayInMonth(CalendarData day) {
        Calendar cal = Calendar.getInstance();
        cal.set(day.year, day.month - 1, 1);
        dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return dayOfWeek;
    }


    public static int getWeekdayOfEndDayInMonth(CalendarData day) {
        CalendarData theDayOfNextMonth = getTheDayOfNextMonth(day);
        return getWeekOfLastDay(getWeekdayOfFirstDayInMonth(theDayOfNextMonth));
    }

    public static int getWeekDay(CalendarData day) {
        Calendar cal = Calendar.getInstance();
        cal.set(day.year, day.month - 1, day.day);
        eachDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return eachDayOfWeek;
    }


    public static List<CalendarData> getWholeMonthDay(CalendarData day) {
        List<CalendarData> datas = new ArrayList<>();
        int weekdayOfFirstDayInMonth = getWeekdayOfFirstDayInMonth(day);
        int weekdayOfLastDayInMonth = getWeekdayOfEndDayInMonth(day);

        datas.addAll(getWholeMonth(day));

        for (int i = 0; i < weekdayOfFirstDayInMonth; i++) {
            CalendarData lastDay = getDayOfLastDay(datas.get(0));
            lastDay.isLastMonthDay = true;
            datas.add(0, lastDay);
        }


        for (int i = 0; i < 6 - weekdayOfLastDayInMonth; i++) {
            CalendarData nextday = getDayOfNextDay(datas.get(datas.size() - 1));
            nextday.isNextMonthDay = true;
            datas.add(nextday);
        }
        return datas;
    }


    public static List<CalendarData> getWholeMonth(CalendarData day) {
        List<CalendarData> datas = new ArrayList<>();
        int monthDay = getDaysOfMonth(day);
        for (int i = 0; i < monthDay; i++) {
            CalendarData c = new CalendarData(day.year, day.month, i + 1);
            datas.add(c);
        }
        return datas;
    }


    public static Map<Integer, List> getWholeWeeks(List<CalendarData> datas) {
        Map<Integer, List> map = new LinkedHashMap<>();
        int weekSize = datas.size() / 7;//当月有几个星期
        for (int i = 0; i < weekSize; i++) {
            List<CalendarData> week = new ArrayList<>();
            for (int j = i * 7; j < i * 7 + 7; j++) {
                week.add(datas.get(j));
                map.put(i, week);
            }
        }
        return map;
    }

    public static int getTheWeekPosition(Map<Integer, List> map, CalendarData day) {
        int position =-1;
        for (Map.Entry<Integer, List> entry : map.entrySet()) {
            position++;
            List<CalendarData> list = (List<CalendarData>) entry.getValue();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).day == day.day && list.get(i).month == day.month) {
                    return  position;
                }
            }
        }

        return 0;
    }


    public static CalendarData getDayOfLastDay(CalendarData theday) {
        CalendarData lastday = new CalendarData();
        lastday.week = getWeekOfLastDay(theday.week);
        CalendarData theDayOfLastMonth = getTheDayOfLastMonth(theday);
        if (theday.day == 1) {
            lastday.day = getDaysOfMonth(theDayOfLastMonth);
            lastday.month = theDayOfLastMonth.month;
            lastday.year = theDayOfLastMonth.year;
        } else {
            lastday.day = theday.day - 1;
            lastday.month = theday.month;
            lastday.year = theday.year;
        }
        return lastday;

    }

    public static CalendarData getDayOfNextDay(CalendarData theday) {
        CalendarData nextday = new CalendarData();
        CalendarData theDayOfNextMonth = getTheDayOfNextMonth(theday);
        nextday.week = getWeekOfNextDay(theday.week);
        if (theday.day == getDaysOfMonth(theday)) {
            nextday.day = 1;
            nextday.month = theDayOfNextMonth.month;
            nextday.year = theDayOfNextMonth.year;
        } else {
            nextday.day = theday.day + 1;
            nextday.month = theday.month;
            nextday.year = theday.year;
        }
        return nextday;

    }

    public static int getWeekOfLastDay(int week) {
        int weekOfLastDay;
        if (week == 0) {
            weekOfLastDay = 6;
        } else {
            weekOfLastDay = week - 1;
        }
        return weekOfLastDay;

    }

    public static int getWeekOfNextDay(int week) {
        int weekOfNextDay;
        if (week == 6) {
            weekOfNextDay = 0;
        } else {
            weekOfNextDay = week + 1;
        }
        return weekOfNextDay;

    }

    public static CalendarData getTheDayOfNextMonth(CalendarData day) {
        CalendarData data = new CalendarData();
        int month = day.month;
        if (month == 12) {
            data.month = 1;
            data.year = day.year + 1;
            data.day = day.day;
        } else {
            data.month = month + 1;
            data.year = day.year;
            data.day = day.day;
        }
        return data;
    }

    public static CalendarData getTheDayOfLastMonth(CalendarData day) {
        CalendarData data = new CalendarData();
        int month = day.month;
        if (month == 1) {
            data.month = 12;
            data.year = day.year - 1;
            data.day = day.day;
        } else {
            data.month = month - 1;
            data.year = day.year;
            data.day = day.day;
        }
        return data;
    }
}
