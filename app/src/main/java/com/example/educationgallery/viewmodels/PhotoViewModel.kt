package com.example.educationgallery.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educationgallery.model.App
import com.example.educationgallery.model.WeekDay
import com.example.educationgallery.ui.getAllPhotos
import com.example.educationgallery.ui.getDateTaken
import com.example.educationgallery.ui.models.LessonFolderView
import com.example.educationgallery.ui.models.SubjectFolderView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date

class PhotoViewModel : ViewModel() {
    private val _subjects = MutableStateFlow<List<SubjectFolderView>>(emptyList())
    val subjects = _subjects.asStateFlow()

    private val _lessons = MutableStateFlow<List<LessonFolderView>>(emptyList())
    val lessons = _lessons.asStateFlow()

    private val _photos = MutableStateFlow<List<String>>(emptyList())
    val photos = _photos.asStateFlow()

    private val scheduleDB = App.dataBase.dayDao()
    private val tag = "PhotoVM"
    private val timeMap = mapOf(
        0 to "9:00-10:30",
        1 to "10:40-12:10",
        2 to "12:40-14:10",
        3 to "14:20-15:50",
        4 to "16:20-17:50",
        5 to "18:00-19:30",
        6 to "19:40-21:10"
    )

    init {
        getSubjects()
    }

    private fun convertId(number1: Int, number2: Int, week: Int): Int {
        return (number1.toString() + number2.toString() + week.toString()).toInt()
    }

    private fun getSubjects() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val allSubject = mutableSetOf<SubjectFolderView>()
                val uniqueSubjects = mutableMapOf<String, SubjectFolderView>()
                WeekDay.entries.flatMap { dayOfWeek ->
                    scheduleDB.findByName(dayOfWeek)?.let { daySchedule ->
                        (daySchedule.oddWeek.filterNotNull() + daySchedule.evenWeek.filterNotNull())
                            .map { lesson ->
                                lesson.name to SubjectFolderView(
                                    convertId(
                                        daySchedule.uid,
                                        lesson.time,
                                        if (daySchedule.oddWeek.contains(lesson)) 1 else 2
                                    ),
                                    lesson.name
                                )
                            }
                    } ?: emptyList()
                }.associateBy(
                    { it.first },
                    { it.second }
                ).also { uniqueSubjects.putAll(it) }
                allSubject.addAll(uniqueSubjects.values)
                _subjects.value = allSubject.toList()
                Log.d(tag, subjects.value.toString())
            }
        }
    }

    fun getLessons(subjectId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val changeDay = scheduleDB.loadAllByIds(
                    intArrayOf(subjectId.toString().dropLast(2).toInt())
                )[0]
                val subjectName = when (subjectId % 10) {
                    1 -> changeDay.oddWeek[(subjectId % 100) / 10].name
                    else -> changeDay.evenWeek[(subjectId % 100) / 10].name
                }
                val tempLessons: MutableList<LessonFolderView> = mutableListOf()
                WeekDay.entries.forEach { dayOfWeek ->
                    scheduleDB.findByName(dayOfWeek)?.let { daySchedule ->
                        tempLessons.addAll((
                                    daySchedule.oddWeek.filterNotNull() +
                                    daySchedule.evenWeek.filterNotNull()
                                ).filter { it.name == subjectName }
                                    .map { lesson ->
                                    LessonFolderView(
                                        convertId(
                                            daySchedule.uid,
                                            lesson.time,
                                            if (daySchedule.oddWeek.contains(lesson)) 1 else 2
                                        ),
                                        timeMap[lesson.time]!! +
                                                if (daySchedule.oddWeek.contains(lesson))
                                                    " Нечетная неделя"
                                                else " Четная неделя",
                                        lesson.type
                                    )
                                }
                        )
                    }
                }
                _lessons.value = tempLessons
            }
        }
        Log.d(tag, "getLessons $subjectId")
    }

    fun getPhotos(subjectId: Int, lessonId: Int) {
        val photos = getAllPhotos(App.instance)
        val filteredPhotos = photos.filter { photo ->
            val dateTaken = getDateTaken(App.instance, photo)
            dateTaken != null && isDateMatching(lessonId, dateTaken)
        }
        _photos.value = filteredPhotos.map { it.toString() }
        Log.d(tag, "getPhotos $subjectId, $lessonId")
    }

    private fun isDateMatching(subjectId: Int, date: Date): Boolean {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
        val weekParity = if (calendar.get(Calendar.WEEK_OF_YEAR) % 2 == 0) 1 else 2
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val dayCompare = subjectId / 100
        val weekCompare = subjectId % 10
        val time = timeMap[(subjectId / 10) % 10]!!
        val parts = time.split("-")
        val startTime = parts[0]
        val endTime = parts[1]
        val (hoursStart, minutesStart) = startTime.split(":")
        val (hoursEnd, minutesEnd) = endTime.split(":")
        return dayOfWeek == dayCompare &&
                weekParity == weekCompare &&
                hoursStart.toInt() <= hour && hour <= hoursEnd.toInt() &&
                minutesStart.toInt() <= minute && minute <= minutesEnd.toInt()
    }
}