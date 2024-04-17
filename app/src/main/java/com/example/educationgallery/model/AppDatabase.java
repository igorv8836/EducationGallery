package com.example.educationgallery.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {Day.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase
{
    public abstract DayDao dayDao();
}
