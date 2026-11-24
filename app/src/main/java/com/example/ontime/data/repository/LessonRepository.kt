package com.example.ontime.data.repository

import com.example.ontime.data.local.LessonDao
import com.example.ontime.data.local.toEntity
import com.example.ontime.data.local.toLesson
import com.example.ontime.data.model.Lesson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object LessonRepository {

    private lateinit var dao: LessonDao
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _lessons = MutableStateFlow<List<Lesson>>(emptyList())
    val lessons: StateFlow<List<Lesson>> = _lessons.asStateFlow()

    fun init(lessonDao: LessonDao) {
        dao = lessonDao
        scope.launch {
            dao.getAllLessons().collect { entities ->
                _lessons.value = entities.map { it.toLesson() }
            }
        }
    }

    fun getLessonById(id: Int): Lesson? =
        _lessons.value.find { it.id == id }

    fun addLesson(lesson: Lesson) {
        scope.launch { dao.insert(lesson.toEntity().copy(id = 0)) }
    }

    fun updateLesson(lesson: Lesson) {
        scope.launch { dao.update(lesson.toEntity()) }
    }

    fun deleteLesson(id: Int) {
        val lesson = getLessonById(id) ?: return
        scope.launch { dao.delete(lesson.toEntity()) }
    }
}