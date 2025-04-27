package com.example.studentmanager.utils;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    // Agar List<String> kabi kompleks turlarni saqlash kerak bo'lsa:
    @TypeConverter
    public static String fromList(List<String> list) {
        return list == null ? null : String.join(",", list);
    }

    @TypeConverter
    public static List<String> toList(String data) {
        return data == null ? null : Arrays.asList(data.split(","));
    }
}