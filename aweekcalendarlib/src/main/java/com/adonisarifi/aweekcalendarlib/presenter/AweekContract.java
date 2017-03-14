package com.adonisarifi.aweekcalendarlib.presenter;

import android.widget.GridView;

import com.adonisarifi.aweekcalendarlib.BasePresenter;
import com.adonisarifi.aweekcalendarlib.BaseView;
import com.adonisarifi.aweekcalendarlib.utils.CalendarData;

import java.util.List;

/**
 * Created by AArifi on 3/14/2017
 * Project:AweekCalendar
 * Email "adonisarifi@gmail.com"
 */

public interface AweekContract {

    interface View extends BaseView<Presenter> {

        void initDate();

        void initView();

        void getWholeMonthDatas(CalendarData data);

        GridView addDayonView();

        void getToday();

        void showNextView(boolean isShowNextWeek);

        void showLastView(boolean isShowLastWeek);

        List<CalendarData> getNextWeekDatas(boolean isShowNextWeek);

        List<CalendarData> getLastWeekDatas(boolean isShowLastWeek);

        String getTheDayOfSelected();

        boolean isTodayIsSelectedDay();


        boolean showToday();


    }


    interface Presenter extends BasePresenter {

    }
}
