package com.adonisarifi.aweekcalendarlib;

/**
 * Created by AArifi on 3/14/2017
 * Project:AweekCalendar
 * Email "adonisarifi@gmail.com"
 */
public interface BaseView<T> {

    void setPresenter(T presenter);

    void showMessage(String message);

    void showErrorMessage(Throwable throwable);

}
