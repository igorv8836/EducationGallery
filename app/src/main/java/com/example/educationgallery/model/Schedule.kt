package com.example.educationgallery.model

class Schedule(
    private var even_numbered_week: ArrayList<DaySchedule>,
    private var not_even_numbered_week: ArrayList<DaySchedule>) {


    fun createSchedule(){
        TODO()
    }
}

data class DaySchedule(
    private var weekDay: WeekDay,
    private var lessons: ArrayList<Lesson>)

data class Lesson(private var name: String, private var time: LessonTime)