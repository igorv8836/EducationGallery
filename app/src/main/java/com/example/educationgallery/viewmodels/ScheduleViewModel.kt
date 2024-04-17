package com.example.educationgallery.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScheduleViewModel : ViewModel() {
    private val _schedule = MutableStateFlow<List<String>>(emptyList())
    val schedule = _schedule.asStateFlow()


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

    fun deleteSchedule(){
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