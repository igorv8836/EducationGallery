package com.example.educationgallery.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {

    @TypeConverter
    public static ArrayList<Lesson> stringToListServer(String data)
    {
        Type listType = new TypeToken<ArrayList<Lesson>>() {}.getType();
        return new Gson().fromJson(data, listType);
    }
    @TypeConverter
    public static String listServerToString(ArrayList<Lesson> someObjects) {
        return new Gson().toJson(someObjects);
    }

    public Day fillDay(WeekDay weekDay, ArrayList<Lesson> oddWeek, ArrayList<Lesson> evenWeek)
    {
        Day day = new Day();

        day.day = weekDay;
        day.oddWeek = oddWeek;
        day.evenWeek = evenWeek;

        return day;
    }
}
