package com.example.today_project.database;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class Converters {
    @TypeConverter
    public static Calendar toCalendar(Long value) {
        if (value == null) {
            return null;
        }else{
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(value);
            return cal;
        }
    }

    @TypeConverter
    public static Long fromCalendar(Calendar date) {
        return date == null ? null : date.getTimeInMillis();
    }
}
