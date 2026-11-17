package com.example.ontime.data.repository

import com.example.ontime.data.model.Lesson
import com.example.ontime.data.model.LessonType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object LessonRepository {

    private val _lessons = MutableStateFlow(
        listOf(
            Lesson(1, "INF 345", LessonType.OTHER, "06-N", "VR 22", 1, "12:30", "14:20"),
            Lesson(2, "CSS 251", LessonType.LAB, "14-L", "VR 2", 2, "15:30", "17:20"),
            Lesson(3, "CSS 312", LessonType.LECTURE, "01-N", "G304", 3, "11:30", "12:20")
        )
    )
    val lessons: StateFlow<List<Lesson>> = _lessons.asStateFlow()

    private var nextId = 4

    fun getLessonById(id: Int): Lesson? =
        _lessons.value.find { it.id == id }

    fun addLesson(lesson: Lesson) {
        _lessons.update { it + lesson.copy(id = nextId++) }
    }

    fun updateLesson(lesson: Lesson) {
        _lessons.update { list -> list.map { if (it.id == lesson.id) lesson else it } }
    }

    fun deleteLesson(id: Int) {
        _lessons.update { list -> list.filterNot { it.id == id } }
    }
}