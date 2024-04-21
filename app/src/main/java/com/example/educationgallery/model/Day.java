package com.example.educationgallery.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "days")
public class Day
{
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "day")
    public WeekDay day;

    @ColumnInfo(name = "odd")
    public ArrayList<Lesson> oddWeek;

    @ColumnInfo(name = "evenweek")
    public ArrayList<Lesson> evenWeek;

    @NonNull
    @Override
    public String toString() {
        return "Day{" +
                "uid=" + uid +
                ", day=" + day +
                ", oddWeek=" + oddWeek +
                ", evenWeek=" + evenWeek +
                '}';
    }
}
