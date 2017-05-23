package com.adonisarifi.aweekcalendarlib.utils;

import android.widget.GridView;

import java.util.List;

/**
 * Created by AArifi on 3/14/2017
 * Project:AweekCalendar
 * Email "adonisarifi@gmail.com"
 */

public interface AweekContract {

    void initDate();

    void initView();

    void getWholeMonthDatas(CalendarData data);

    GridView addDayonView();

    void getToday();

    int getMonthSelected();

    int getDayOfMonthSelected();

    int getYearSelected();

    void showNextView(boolean isShowNextWeek);

    void showLastView(boolean isShowLastWeek);

    List<CalendarData> getNextWeekDatas(boolean isShowNextWeek);

    List<CalendarData> getLastWeekDatas(boolean isShowLastWeek);

    String getTheDayOfSelected();

    boolean isTodayIsSelectedDay();


    boolean showToday();

}
