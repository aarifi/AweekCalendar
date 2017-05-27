package com.adonisarifi.aweekcalendarlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.adonisarifi.aweekcalendarlib.R;
import com.adonisarifi.aweekcalendarlib.utils.AweekContract;
import com.adonisarifi.aweekcalendarlib.utils.CalendarData;
import com.adonisarifi.aweekcalendarlib.utils.Calendarutil;
import com.adonisarifi.aweekcalendarlib.utils.OnItemClickLitener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by AArifi on 3/14/2017
 * Project:AweekCalendar
 * Email "adonisarifi@gmail.com"
 */

public class AweekCalendarView extends LinearLayout implements AweekContract {
    RelativeLayout mIvPrevious;
    TextView mTvYearMouth;
    RelativeLayout mIvNext;
    ViewFlipper mRvDay;

    private Context context;
    private GridView mGridView = null;
    private OnItemClickLitener mOnItemClickLitener;

    private List<CalendarData> calendarDatas;
    private Map<Integer, List> weeks;
    private int weekPosition;


    private CalendarData today;
    private CalendarData theDayOfSelected;
    private CalendarData theDayForShow;

    private int count_previous = 0;
    private int count_next = 0;

    public AweekCalendarView(Context context) {
        super(context);
        init(context, null);
    }

    public AweekCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        try {
            this.context = context;
            LayoutInflater.from(context).inflate(R.layout.view_calender, this, true);
            mIvPrevious = (RelativeLayout) findViewById(R.id.iv_previous);
            mTvYearMouth = (TextView) findViewById(R.id.tv_year_mouth);
            mIvNext = (RelativeLayout) findViewById(R.id.iv_next);
            mRvDay = (ViewFlipper) findViewById(R.id.rv_day);
            initDate();
            initView();
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ViewCalender);
            array.recycle();
        } catch (Exception e) {
            e.getMessage();
        }

    }


    private float mLastX = -1;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = mLastX - event.getRawX();
                if (dx > 80) {
                    if (count_next < 2) {
                        count_next++;
                        count_previous = count_previous - 1;
                        showNextView(true);
                    }

                    return true;
                } else if (dx < -80) {
                    if (count_previous < 2) {
                        count_next = count_next - 1;
                        count_previous++;
                        showLastView(true);
                    }

                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(event);
    }


    @Override
    public void initDate() {
        calendarDatas = new ArrayList<>();
        getToday();
        theDayOfSelected = today;
        theDayForShow = today;
        getWholeMonthDatas(theDayOfSelected);
        weekPosition = Calendarutil.getTheWeekPosition(weeks, theDayOfSelected);
    }

    @Override
    public void initView() {

        mIvPrevious.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count_previous < 2) {
                    count_next = count_next - 1;
                    count_previous++;
                    showLastView(true);
                }


            }
        });
        mIvNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count_next < 2) {
                    count_next++;
                    count_previous = count_previous - 1;
                    showNextView(true);
                }

            }
        });
        mGridView = addDayonView();
        mGridView.setAdapter(new CalendarAdapter(context, weeks.get(weekPosition)));
        mRvDay.addView(mGridView, 0);
    }

    @Override
    public void getWholeMonthDatas(CalendarData data) {
        calendarDatas = Calendarutil.getWholeMonthDay(data);
        weeks = Calendarutil.getWholeWeeks(calendarDatas);
        mTvYearMouth.setText(data.toString());
    }

    @Override
    public GridView addDayonView() {
        LayoutParams params = new LayoutParams(
                AbsListView.LayoutParams.FILL_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        final GridView gridView = new GridView(context);
        gridView.setNumColumns(7);
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);
//        gridView.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                return false;
//            }
//        });
        gridView.setLayoutParams(params);
        return gridView;
    }

    @Override
    public void getToday() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        String currentDate = sdf.format(date);
        int year = Integer.parseInt(currentDate.split("-")[0]);
        int month = Integer.parseInt(currentDate.split("-")[1]);
        int day = Integer.parseInt(currentDate.split("-")[2]);
        today = new CalendarData(year, month, day);
    }

    @Override
    public int getMonthSelected() {
        return theDayOfSelected.month;
    }

    @Override
    public int getDayOfMonthSelected() {
        return theDayOfSelected.day;
    }

    @Override
    public int getYearSelected() {
        return theDayOfSelected.year;
    }

    @Override
    public void showNextView(boolean isShowNextWeek) {
        GridView mGridView = addDayonView();
        mGridView.setAdapter(new CalendarAdapter(context, getNextWeekDatas(isShowNextWeek)));
        mRvDay.addView(mGridView, 1);
        mRvDay.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_in));
        mRvDay.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_out));
        mRvDay.showNext();
        mRvDay.removeViewAt(0);
    }

    @Override
    public void showLastView(boolean isShowLastWeek) {
        GridView mGridView = addDayonView();
        mGridView.setAdapter(new CalendarAdapter(context, getLastWeekDatas(isShowLastWeek)));
        mRvDay.addView(mGridView, 1);
        mRvDay.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_in));
        mRvDay.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_out));
        mRvDay.showNext();
        mRvDay.removeViewAt(0);
    }

    @Override
    public List<CalendarData> getNextWeekDatas(boolean isShowNextWeek) {
        if (weekPosition == weeks.size() - 1 || !isShowNextWeek) {
            theDayForShow = (theDayForShow.isNextMonthDay) ? theDayForShow : Calendarutil.getTheDayOfNextMonth(theDayForShow);
            getWholeMonthDatas(theDayForShow);
            weekPosition = (Calendarutil.getWeekdayOfFirstDayInMonth(theDayForShow) == 0 || !isShowNextWeek) ? 0 : 1;
        } else {
            weekPosition++;
        }

        return (List<CalendarData>) weeks.get(weekPosition);
    }

    @Override
    public List<CalendarData> getLastWeekDatas(boolean isShowLastWeek) {
        if (weekPosition == 0 || !isShowLastWeek) {
            theDayForShow = (theDayForShow.isLastMonthDay) ? theDayForShow : Calendarutil.getTheDayOfLastMonth(theDayForShow);
            getWholeMonthDatas(theDayForShow);
            if (isShowLastWeek) {
                weekPosition = weeks.size() - ((Calendarutil.getWeekdayOfEndDayInMonth(theDayForShow) == 6) ? 1 : 2);
            } else {
                weekPosition = 0;
            }
        } else {
            weekPosition--;
        }
        return (List<CalendarData>) weeks.get(weekPosition);
    }

    @Override
    public String getTheDayOfSelected() {
        if (theDayOfSelected != null) {
            String sYear = String.valueOf(theDayOfSelected.year);
            String sMonth = String.valueOf(theDayOfSelected.month);
            String sDay = String.valueOf(theDayOfSelected.day);
            return String.format("%s-%s-%s", sYear, (2 > sMonth.length()) ? "0" + sMonth : "" + sMonth, (2 > sDay.length()) ? "0" + sDay : "" + sDay);
        }
        return "";
    }

    @Override
    public boolean isTodayIsSelectedDay() {
        return today.isSameDay(theDayForShow) && today.isSameDay(theDayOfSelected);
    }

    @Override
    public boolean showToday() {
        if (!isTodayIsSelectedDay() || weekPosition != Calendarutil.getTheWeekPosition(weeks, today)) {
            int mode = 0;
            if (theDayForShow.year > today.year || theDayForShow.month > today.month) {
                getWholeMonthDatas(today);
                weekPosition = Calendarutil.getTheWeekPosition(weeks, today);
                mode = 2;
            } else if (theDayForShow.year < today.year || theDayForShow.month < today.month) {
                getWholeMonthDatas(today);
                weekPosition = Calendarutil.getTheWeekPosition(weeks, today);
                mode = 1;
            } else {
                int position = Calendarutil.getTheWeekPosition(weeks, today);
                if (weekPosition < position) {
                    mode = 1;
                } else if (weekPosition > position) {
                    mode = 2;
                }
                weekPosition = position;
            }

            theDayOfSelected = today;
            theDayForShow = today;
            GridView mGridView = addDayonView();
            mGridView.setAdapter(new CalendarAdapter(context, weeks.get(weekPosition)));
            mRvDay.addView(mGridView, 1);
            if (mode == 2) {
                mRvDay.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_in));
                mRvDay.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_out));
            } else if (mode == 1) {
                mRvDay.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_in));
                mRvDay.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_out));
            } else {
                mRvDay.setInAnimation(null);
                mRvDay.setOutAnimation(null);
            }
            mRvDay.showNext();
            mRvDay.removeViewAt(0);
            return false;
        }
        return true;
    }


    public class CalendarAdapter extends AweekCalendarAdapter {
        List<CalendarData> datas;

        public CalendarAdapter(Context context, List<CalendarData> datas) {
            super(context, datas);
            this.datas = datas;
        }

        @Override
        public int getItemResource() {
            return R.layout.item_calendar;
        }

        @Override
        public View getItemView(final int position, View convertView, final ViewHolder viewHolder) {
            final CalendarData calendar = (CalendarData) getItem(position);
            final TextView dayView = (TextView) viewHolder.getView(R.id.tv_calendar_day);
            final TextView weekView = (TextView) viewHolder.getView(R.id.tv_calendar_week);
            weekView.setText(Calendarutil.getWeekString(mContext).get(position));
            dayView.setText(String.valueOf(calendar.day));
            dayView.setSelected(false);
            if (calendar.isSameDay(theDayOfSelected)) {//被选中的日期是白的
                dayView.setTextColor(getResources().getColor(R.color.white));
                dayView.setSelected(true);//设置选中背景
            } else if (calendar.isLastMonthDay || calendar.isNextMonthDay) {//上一个月、下一个月的日期是灰色的
                dayView.setTextColor(getResources().getColor(R.color.color_d1));
            } else if (calendar.isSameDay(today)) {//当天的日期是橘黄色的
                dayView.setTextColor(getResources().getColor(R.color.color_orange));
            } else {
                dayView.setTextColor(getResources().getColor(R.color.color_88));
            }
            //如果设置了回调，则设置点击事件
            dayView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    theDayOfSelected = datas.get(position);
                    theDayForShow = datas.get(position);
                    notifyDataSetChanged();
                    Log.e("myCalender", calendar.year + "年" + calendar.month + "月" + calendar.day);
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.onItemClick(dayView, position);
                    }
                }
            });
            return convertView;
        }
    }


    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


}
