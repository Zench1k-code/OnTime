package com.example.ontime.ui.schedule

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ontime.data.model.Lesson
import com.example.ontime.data.repository.LessonRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class LessonDetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val lessonId: Int = checkNotNull(savedStateHandle["lessonId"])

    val lesson: StateFlow<Lesson?> =
        LessonRepository.lessons
            .map { lessons -> lessons.find { it.id == lessonId } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = LessonRepository.getLessonById(lessonId)
            )

    fun deleteLesson() {
        LessonRepository.deleteLesson(lessonId)
    }
}