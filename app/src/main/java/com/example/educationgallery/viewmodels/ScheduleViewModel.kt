package com.example.educationgallery.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.educationgallery.ui.models.DaySchedule
import com.example.educationgallery.ui.models.LessonView
import com.example.educationgallery.ui.models.TwoWeekScheduleView
import com.example.educationgallery.ui.models.WeekScheduleView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScheduleViewModel : ViewModel() {
    private val _lessonsTypes = MutableStateFlow<List<String>>(emptyList())
    val lessonsTypes = _lessonsTypes.asStateFlow()

    private val _lessonsTimes = MutableStateFlow<List<String>>(emptyList())
    val lessonsTimes = _lessonsTimes.asStateFlow()

    private val _lessons = MutableStateFlow<TwoWeekScheduleView?>(null)
    val lessons = _lessons.asStateFlow()

    private val _filteredScheduleName = MutableStateFlow<List<String>>(emptyList())
    val filteredScheduleName = _filteredScheduleName.asStateFlow()


    init {
        getSchedule()
        getLessonsTypes()
        getLessonTimes()
    }


    fun getSchedule() {
        //        TODO()
        _lessons.value = TwoWeekScheduleView(
            WeekScheduleView(
                List(7){
                    DaySchedule(
                        1,
                        listOf(
                            LessonView(
                                10,
                                "testing",
                                "ЛК",
                                "Понедельник",
                                "16:10-15:40"
                            )
                        )
                    )
                }
            ),
            WeekScheduleView(
                List(7){
                    DaySchedule(
                        1,
                        listOf(
                            LessonView(
                                1,
                                "testing",
                                "ЛК",
                                "Понедельник",
                                "16:10-15:40"
                            )
                        )
                    )
                }
            )
        )
        Log.d("ScheduleVM", "getSchedule")
    }

    fun addLesson(name: String, selectedTime: String, selectedType: String) {
        //        TODO()
        Log.d("ScheduleVM", "addLesson $name, $selectedTime, $selectedType")
    }

    fun deleteLesson(id: Int) {
        //        TODO()
        Log.d("ScheduleVM", "deleteLesson $id")
    }

    fun changeSchedule(id: Int, name: String, selectedTime: String, selectedType: String) {
        //        TODO()
        Log.d("ScheduleVM", "changeSchedule $id, $name, $selectedTime, $selectedType")
    }

    fun getLessonsTypes(){
        _lessonsTypes.value = listOf("Лекция", "Практика")
        //        TODO()
        Log.d("ScheduleVM", "getLessonsTypes")
    }

    fun getLessonTimes(){
        _lessonsTimes.value = listOf("9:00-10:30", "10:40-12:10", "12:40-14:10", "14:20-15:50", "16:20-17:50")
        //        TODO()
        Log.d("ScheduleVM", "getLessonTimes")
    }

    fun getFilteredScheduleName(text: String){
        //        TODO()
        Log.d("ScheduleVM", "getFilteredScheduleName $text")
    }




}