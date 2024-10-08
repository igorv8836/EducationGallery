package com.example.educationgallery.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educationgallery.model.App
import com.example.educationgallery.model.Lesson
import com.example.educationgallery.model.WeekDay
import com.example.educationgallery.ui.models.DaySchedule
import com.example.educationgallery.ui.models.LessonView
import com.example.educationgallery.ui.models.TwoWeekScheduleView
import com.example.educationgallery.ui.models.WeekScheduleView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScheduleViewModel : ViewModel() {
    private val _lessonsTypes = MutableStateFlow(
        listOf(
            "ЛК",
            "ПР"
        )
    )
    val lessonsTypes = _lessonsTypes.asStateFlow()

    private val _lessonsTimes = MutableStateFlow(
        listOf(
            "9:00-10:30",
            "10:40-12:10",
            "12:40-14:10",
            "14:20-15:50",
            "16:20-17:50",
            "18:00-19:30",
            "19:40-21:10"
        )
    )
    val lessonsTimes = _lessonsTimes.asStateFlow()

    private val _lessons = MutableStateFlow<TwoWeekScheduleView?>(null)
    val lessons = _lessons.asStateFlow()

    private val _filteredScheduleName = MutableStateFlow<List<String>>(emptyList())
    val filteredScheduleName = _filteredScheduleName.asStateFlow()

    private val scheduleDB = App.dataBase.dayDao()
    private val tag = "ScheduleVM"
    private val dayOfWeekMap = mapOf(
        WeekDay.MONDAY to 1,
        WeekDay.TUESDAY to 2,
        WeekDay.WEDNESDAY to 3,
        WeekDay.THURSDAY to 4,
        WeekDay.FRIDAY to 5,
        WeekDay.SATURDAY to 6,
        WeekDay.SUNDAY to 7
    )
    private val timeMap = mapOf(
        "9:00-10:30" to 0,
        "10:40-12:10" to 1,
        "12:40-14:10" to 2,
        "14:20-15:50" to 3,
        "16:20-17:50" to 4,
        "18:00-19:30" to 5,
        "19:40-21:10" to 6
    )

    init {
        getSchedule()
    }

    private fun getSchedule() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                scheduleDB.all.collect{
                    val weekOddSchedule: MutableList<DaySchedule> = mutableListOf()
                    val weekEvenSchedule: MutableList<DaySchedule> = mutableListOf()
                    for (dayOfWeek in WeekDay.entries) {
                        val daySchedule = scheduleDB.findByName(dayOfWeek)
                        if (daySchedule != null) {
                            val dayOddScheduleList = DaySchedule(
                                dayOfWeekMap[dayOfWeek]!!,
                                daySchedule.oddWeek.filterNotNull().map { lesson ->
                                    LessonView(
                                        (daySchedule.uid.toString() +
                                                lesson.time.toString() +
                                                1.toString()).toInt(),
                                        lesson.name,
                                        lesson.type,
                                        dayOfWeek.name,
                                        _lessonsTimes.value[lesson.time]
                                    )
                                }
                            )
                            val dayEvenScheduleList = DaySchedule(
                                dayOfWeekMap[dayOfWeek]!!,
                                daySchedule.evenWeek.filterNotNull().map { lesson ->
                                    LessonView(
                                        (daySchedule.uid.toString() +
                                                lesson.time.toString() +
                                                2.toString()).toInt(),
                                        lesson.name,
                                        lesson.type,
                                        dayOfWeek.name,
                                        _lessonsTimes.value[lesson.time]
                                    )
                                }
                            )
                            weekOddSchedule.add(dayOddScheduleList)
                            weekEvenSchedule.add(dayEvenScheduleList)
                            Log.d(tag, dayEvenScheduleList.toString())
                        } else {
                            Log.d(tag, "null day")
                        }
                    }
                    _lessons.value = TwoWeekScheduleView(
                        oddWeek = WeekScheduleView(weekOddSchedule),
                        evenWeek = WeekScheduleView(weekEvenSchedule)
                    )
                }

            }
        }
        Log.d(tag, "getSchedule")
    }

    fun addLesson(name: String, selectedTime: String, selectedType: String, dayNumber: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val time = timeMap[selectedTime]!!
                val lesson = Lesson(selectedType, name, time)
                val addDay = scheduleDB.findByName(WeekDay.entries[dayNumber % 7])
                if (addDay.oddWeek == null)
                    addDay.oddWeek = ArrayList()
                if (addDay.evenWeek == null)
                    addDay.evenWeek = ArrayList()
                if (dayNumber < 7) {
                    addDay.oddWeek[time] = lesson
                } else {
                    addDay.evenWeek[time] = lesson
                }
                scheduleDB.updateDay(addDay)
            }
            getSchedule()
        }
        Log.d(tag, "addLesson $name, $selectedTime, $selectedType")
    }

    fun deleteLesson(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val changeDay = scheduleDB.loadAllByIds(
                    intArrayOf(id.toString().dropLast(2).toInt())
                )[0]
                if (id % 10 == 1) {
                    changeDay.oddWeek[(id % 100) / 10] = null
                } else {
                    changeDay.evenWeek[(id % 100) / 10] = null
                }
                scheduleDB.updateDay(changeDay)
                getSchedule()
            }
        }
        Log.d(tag, "deleteLesson $id")
    }


    fun changeSchedule(id: Int, name: String, selectedTime: String, selectedType: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val changeDay = scheduleDB.loadAllByIds(
                    intArrayOf(id.toString().dropLast(2).toInt())
                )[0]
                val time = timeMap[selectedTime]!!
                val lesson = Lesson(selectedType, name, time)
                val existingLesson =
                    if (id % 10 == 1) changeDay.oddWeek[time] else changeDay.evenWeek[time]
                if (id % 10 == 1) {
                    changeDay.oddWeek[time] = lesson
                } else {
                    changeDay.evenWeek[time] = lesson
                }
                if (existingLesson == null || (id % 100) / 10 != time) {
                    deleteLesson(id)
                }
                scheduleDB.updateDay(changeDay)
                getSchedule()
            }
        }
        Log.d(tag, "changeSchedule $id, $name, $selectedTime, $selectedType")
    }

    fun getFilteredScheduleName(text: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val filteredNames = mutableSetOf<String>()

                for (day in scheduleDB.all.first()) {
                    for (lesson in day.oddWeek) {
                        if (lesson?.name?.contains(text, ignoreCase = true) == true) {
                            filteredNames.add(lesson.name)
                        }
                    }
                    for (lesson in day.evenWeek) {
                        if (lesson?.name?.contains(text, ignoreCase = true) == true) {
                            filteredNames.add(lesson.name)
                        }
                    }
                }

                _filteredScheduleName.value = filteredNames.toList()
            }
        }

        Log.d(tag, "getFilteredScheduleName $text")
    }

}