package com.example.educationgallery.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.educationgallery.ui.models.LessonFolderView
import com.example.educationgallery.ui.models.SubjectFolderView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PhotoViewModel: ViewModel() {
    private val _subjects = MutableStateFlow<List<SubjectFolderView>>(emptyList())
    val subjects = _subjects.asStateFlow()

    private val _lessons = MutableStateFlow<List<LessonFolderView>>(emptyList())
    val lessons = _lessons.asStateFlow()

    private val _photos = MutableStateFlow<List<String>>(emptyList())
    val photos = _photos.asStateFlow()

    init {
        getSubjects()
    }

    private fun getSubjects(){
//        TODO()
        _subjects.value = List(10){ SubjectFolderView(10, "test") }
        Log.d("PhotoVM", "getSubjects")
    }

    fun getLessons(subjectId: Int){
        _lessons.value = List(10){ LessonFolderView(10,"12.10", "Лекция") }
        Log.d("PhotoVM", "getLessons $subjectId")
    }

    fun getPhotos(subjectId: Int, lessonId: Int){
        _photos.value = listOf(
            "https://random.dog/5cd73551-c42b-4c0f-bd80-634998e9a128.jpg",
            "https://random.dog/24178-5036-5513.jpg",
            "https://random.dog/a87a8d31-48dd-45b4-baab-e115dfa70692.jpg",
            "https://random.dog/1ddb2caa-41f5-456f-a446-f5a5335b5811.jpg",
            "https://random.dog/57f9587d-d6b1-4c05-acff-030c6affac57.png"
        )
        Log.d("PhotoVM", "getPhotos $subjectId, $lessonId")
    }

}