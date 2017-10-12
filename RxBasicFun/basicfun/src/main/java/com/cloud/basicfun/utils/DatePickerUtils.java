package com.cloud.basicfun.utils;

import android.app.Activity;

import com.cloud.core.utils.DateUtils;
import com.cloud.resources.picker.DatePicker;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/11/7
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public class DatePickerUtils {

    private int startYear = 1971;
    private int startMonth = 1;
    private int startDay = 1;
    private int endYear = 0;
    private int endMonth = 0;
    private int endDay = 0;
    private int year = 0;
    private int month = 0;
    private int day = 0;
    private Object extras = null;

    protected void onDateSelected(String year, String month, String day) {

    }

    public void setRangeStart(int startYear, int startMonth, int startDay) {
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.startDay = startDay;
    }

    public void setRangeEnd(int endYear, int endMonth, int endDay) {
        this.endYear = endYear;
        this.endMonth = endMonth;
        this.endDay = endDay;
    }

    public void setSelectedItem(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Object getExtras() {
        return extras;
    }

    public void setExtras(Object extras) {
        this.extras = extras;
    }

    public void show(Activity activity) {
        DatePicker picker = new DatePicker(activity);
        picker.setRangeStart(startYear, startMonth, startDay);
        if (endYear <= 0) {
            endYear = DateUtils.getCurrentYear();
        }
        if (endMonth <= 0) {
            endMonth = DateUtils.getCurrentMonth();
        }
        if (endDay <= 0) {
            endDay = DateUtils.getCurrDay();
        }
        picker.setRangeEnd(endYear, endMonth, endDay);
        if (year > 0 && month > 0 && day > 0) {
            picker.setSelectedItem(year, month, day);
        }
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                onDateSelected(year, month, day);
            }
        });
        picker.show();
    }
}
