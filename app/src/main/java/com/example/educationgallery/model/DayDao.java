package com.example.educationgallery.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DayDao {
    @Query("SELECT * FROM days")
    List<Day> getAll();

    @Query("SELECT * FROM days WHERE uid IN (:userIds)")
    List<Day> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM days WHERE day LIKE :WeekDay LIMIT 1")
    Day findByName(WeekDay WeekDay);

    @Insert
    void insertAll(Day... days);

    @Delete
    void delete(Day day);
}
