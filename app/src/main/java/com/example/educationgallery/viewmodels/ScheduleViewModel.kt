package com.example.educationgallery.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.educationgallery.ui.models.DaySchedule
import com.example.educationgallery.ui.models.LessonView
import com.example.educationgallery.ui.models.TwoWeekScheduleView
import com.example.educationgallery.ui.models.WeekScheduleView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScheduleViewModel: ViewModel() {
    private val _lessonsTypes = MutableStateFlow<List<String>>(
        listOf(
            "ЛК",
            "ПР"
        )
    )
    val lessonsTypes = _lessonsTypes.asStateFlow()

    private val _lessonsTimes = MutableStateFlow<List<String>>(emptyList())
    val lessonsTimes = _lessonsTimes.asStateFlow()

    private val _lessons = MutableStateFlow<TwoWeekScheduleView?>(null)
    val lessons = _lessons.asStateFlow()

    private val _filteredScheduleName = MutableStateFlow<List<String>>(emptyList())
    val filteredScheduleName = _filteredScheduleName.asStateFlow()


    init {
        getSchedule()
    }

    fun getSchedule() {
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
                        evenWeek = WeekScheduleView(weekOddSchedule)
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
                if (dayNumber < 7){
                    addDay.oddWeek[time] = lesson
                }
                else{
                    addDay.evenWeek[time] = lesson
                }
                scheduleDB.updateDay(addDay)
            }
            getSchedule()
        }
        Log.d(tag, "addLesson $name, $selectedTime, $selectedType")
    }

    fun deleteLesson(id: Int) {
        //        TODO()
        Log.d("ScheduleVM", "deleteLesson $id")
    }

    fun changeSchedule(id: Int, name: String, selectedTime: String, selectedType: String) {
        //        TODO()
        Log.d("ScheduleVM", "changeSchedule $id, $name, $selectedTime, $selectedType")
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
}