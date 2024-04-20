package com.example.educationgallery.viewmodels

import androidx.lifecycle.ViewModel
import com.example.educationgallery.ui.models.TwoWeekScheduleView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScheduleViewModel : ViewModel() {
    private val _lessonsTypes = MutableStateFlow<List<String>>(emptyList())
    val lessonsTypes = _lessonsTypes.asStateFlow()

    private val _lessonsTimes = MutableStateFlow<List<String>>(emptyList())
    val lessonsTimes = _lessonsTypes.asStateFlow()

    private val _lessons = MutableStateFlow<TwoWeekScheduleView?>(null)
    val lessons = _lessonsTypes.asStateFlow()

    private val _filteredScheduleName = MutableStateFlow<List<String>>(emptyList())
    val filteredScheduleName = _filteredScheduleName.asStateFlow()


    init {
        getLessonsTypes()
        getLessonTimes()
    }


    fun getSchedule() {
        //...
        //...
        // Вы где то получаете данные и тут передаете в _schedule.value расписание,
    // а в _schedule меняете тип данных на List<Класс расписания> вместо string
        // _schedule.value = schedule - какое то расписание
        TODO()
    }

    fun addSchedule(){
        TODO("Добавление нового занятия в расписание, параметры будут чуть позже")
    }

    fun deleteLesson(){
        TODO("Удаление занятия из расписания, параметры будут чуть позже")
    }

    fun changeSchedule(){
        TODO("Изменение занятия в расписании, параметры будут чуть позже")
    }

    fun getLessonsTypes(){
        TODO("Получение типов занятий")
    }

    fun getLessonTimes(){
        TODO("Получение времени занятий")
    }

    fun getFilteredScheduleName(text: String){
        TODO("Получение списка названий занятий, где есть выражение <text>")
    }




}