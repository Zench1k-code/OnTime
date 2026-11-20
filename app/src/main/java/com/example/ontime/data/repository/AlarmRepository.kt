package com.example.ontime.data.repository

import com.example.ontime.data.model.LessonAlarm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object AlarmRepository {

    // Only changed alarms are stored. A lesson without an entry uses the default alarm.
    private val _alarms = MutableStateFlow<Map<Int, LessonAlarm>>(emptyMap())
    val alarms: StateFlow<Map<Int, LessonAlarm>> = _alarms.asStateFlow()

    fun getAlarm(lessonId: Int): LessonAlarm =
        _alarms.value[lessonId] ?: LessonAlarm(lessonId)

    fun saveAlarm(alarm: LessonAlarm) {
        _alarms.update { it + (alarm.lessonId to alarm) }
    }

    fun toggleEnabled(lessonId: Int) {
        val alarm = getAlarm(lessonId)
        saveAlarm(alarm.copy(isEnabled = !alarm.isEnabled))
    }

    fun deleteAlarm(lessonId: Int) {
        _alarms.update { it - lessonId }
    }
}
