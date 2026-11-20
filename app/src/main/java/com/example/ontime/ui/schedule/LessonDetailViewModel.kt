package com.example.ontime.ui.schedule

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ontime.data.model.Assignment
import com.example.ontime.data.model.Lesson
import com.example.ontime.data.repository.AlarmRepository
import com.example.ontime.data.repository.AssignmentRepository
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

    /** Assignments linked to this lesson, nearest deadline first. */
    val assignments: StateFlow<List<Assignment>> =
        AssignmentRepository.assignments
            .map { list ->
                list.filter { it.lessonId == lessonId }
                    .sortedWith(compareBy({ it.isDone }, { it.deadline }))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun toggleAssignmentDone(id: Int) {
        AssignmentRepository.toggleDone(id)
    }

    // The lesson's assignments and alarm are removed together with the lesson
    fun deleteLesson() {
        LessonRepository.deleteLesson(lessonId)
        AssignmentRepository.deleteAssignmentsOfLesson(lessonId)
        AlarmRepository.deleteAlarm(lessonId)
    }
}