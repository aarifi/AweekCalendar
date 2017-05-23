package com.adonisarifi.aweekcalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by AArifi on 4/27/2017.
 * Project: AweekCalendar
 * Github: https://github.com/aarifi
 * Email "adonisarifi@gmail.com"
 */


public class FragmentAweek extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View root_view = inflater.inflate(R.layout.fragment_aweek, container, false);

        return root_view;

    }
}
