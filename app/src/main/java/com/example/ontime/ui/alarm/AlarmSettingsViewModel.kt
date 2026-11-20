package com.example.ontime.ui.alarm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.ontime.data.model.Lesson
import com.example.ontime.data.model.LessonAlarm
import com.example.ontime.data.repository.AlarmRepository
import com.example.ontime.data.repository.LessonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

const val MIN_MINUTES_BEFORE = 0
const val MAX_MINUTES_BEFORE = 180

class AlarmSettingsViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val lessonId: Int = checkNotNull(savedStateHandle["lessonId"])

    val lesson: Lesson? = LessonRepository.getLessonById(lessonId)

    // Changes are kept here until the user presses Save
    private val _alarm = MutableStateFlow(AlarmRepository.getAlarm(lessonId))
    val alarm: StateFlow<LessonAlarm> = _alarm.asStateFlow()

    fun setMinutesBefore(minutes: Int) {
        _alarm.update {
            it.copy(minutesBefore = minutes.coerceIn(MIN_MINUTES_BEFORE, MAX_MINUTES_BEFORE))
        }
    }

    fun setEnabled(enabled: Boolean) {
        _alarm.update { it.copy(isEnabled = enabled) }
    }

    fun save() {
        AlarmRepository.saveAlarm(_alarm.value)
    }
}
