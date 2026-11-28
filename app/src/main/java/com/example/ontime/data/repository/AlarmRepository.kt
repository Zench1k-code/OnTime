package com.example.ontime.data.repository

import com.example.ontime.data.local.AlarmDao
import com.example.ontime.data.local.toEntity
import com.example.ontime.data.local.toLessonAlarm
import com.example.ontime.data.model.LessonAlarm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object AlarmRepository {

    private lateinit var dao: AlarmDao
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // Only changed alarms are stored. A lesson without an entry uses the default alarm.
    private val _alarms = MutableStateFlow<Map<Int, LessonAlarm>>(emptyMap())
    val alarms: StateFlow<Map<Int, LessonAlarm>> = _alarms.asStateFlow()

    fun init(alarmDao: AlarmDao) {
        dao = alarmDao
        scope.launch {
            dao.getAllAlarms().collect { entities ->
                _alarms.value = entities.map { it.toLessonAlarm() }.associateBy { it.lessonId }
            }
        }
    }

    fun getAlarm(lessonId: Int): LessonAlarm =
        _alarms.value[lessonId] ?: LessonAlarm(lessonId)

    fun saveAlarm(alarm: LessonAlarm) {
        scope.launch { dao.upsert(alarm.toEntity()) }
    }

    fun toggleEnabled(lessonId: Int) {
        val alarm = getAlarm(lessonId)
        saveAlarm(alarm.copy(isEnabled = !alarm.isEnabled))
    }

    fun deleteAlarm(lessonId: Int) {
        scope.launch { dao.deleteByLessonId(lessonId) }
    }
}
