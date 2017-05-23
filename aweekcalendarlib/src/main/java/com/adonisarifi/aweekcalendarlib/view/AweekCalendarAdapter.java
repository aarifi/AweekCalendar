package com.adonisarifi.aweekcalendarlib.view;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AArifi on 3/14/2017
 * Project:AweekCalendar
 * Email "adonisarifi@gmail.com"
 */

public abstract class AweekCalendarAdapter<T> extends BaseAdapter {

    protected Context mContext;

    protected List data;

    protected AweekCalendarAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.data = data == null ? new ArrayList() : new ArrayList<T>(data);
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (position >= data.size()) {
            return null;
        }
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public abstract int getItemResource();

    public abstract View getItemView(int position, View convertView, ViewHolder holder);

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (null == view) {
            view = View.inflate(mContext, getItemResource(), null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            if (view.getTag() != null) {
                holder = (ViewHolder) view.getTag();

            } else {
                view = View.inflate(mContext, getItemResource(), null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }
        }
        return getItemView(position, view, holder);
    }

    public void addAll(List elem) {
        if (elem == null) {
            elem = new ArrayList();
        }
        data.addAll(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        data.remove(index);
        notifyDataSetChanged();
    }


    public void replaceAll(List elem) {
        data.clear();
        if (elem != null) {
            data.addAll(elem);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void notifyDataChanged() {
        notifyDataSetChanged();
    }


    public class ViewHolder {
        private SparseArray<View> views = new SparseArray<View>();
        private View convertView;

        public ViewHolder(View convertView) {
            this.convertView = convertView;
        }

        public <T extends View> T getView(int resId) {
            View v = views.get(resId);
            if (null == v) {
                v = convertView.findViewById(resId);
                views.put(resId, v);
            }
            return (T) v;
        }
    }
}
