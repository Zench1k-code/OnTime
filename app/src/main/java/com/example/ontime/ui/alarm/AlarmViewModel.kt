package com.example.ontime.ui.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ontime.data.repository.AlarmRepository
import com.example.ontime.data.repository.LessonRepository
import com.example.ontime.util.dayNames
import com.example.ontime.util.minutesUntilRing
import com.example.ontime.util.ringTime
import com.example.ontime.util.timeUntilLabel
import com.example.ontime.util.toMinutes
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class AlarmViewModel : ViewModel() {

    val uiState: StateFlow<AlarmUiState> =
        combine(LessonRepository.lessons, AlarmRepository.alarms) { lessons, _ ->
            val items = lessons
                .sortedWith(compareBy({ it.dayOfWeek }, { toMinutes(it.startTime) }))
                .map { lesson ->
                    val alarm = AlarmRepository.getAlarm(lesson.id)
                    AlarmItemUi(lesson, alarm, ringTime(lesson.startTime, alarm.minutesBefore))
                }

            val next = items
                .filter { it.alarm.isEnabled }
                .minByOrNull {
                    minutesUntilRing(it.lesson.dayOfWeek, it.lesson.startTime, it.alarm.minutesBefore)
                }

            AlarmUiState(
                alarmsByDay = items.groupBy { dayNames[it.lesson.dayOfWeek - 1] },
                nextAlarm = next?.let {
                    NextAlarmUi(
                        lessonName = it.lesson.name,
                        day = dayNames[it.lesson.dayOfWeek - 1],
                        ringTime = it.ringTime,
                        timeUntil = timeUntilLabel(
                            minutesUntilRing(it.lesson.dayOfWeek, it.lesson.startTime, it.alarm.minutesBefore)
                        )
                    )
                }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AlarmUiState()
        )

    fun toggleAlarm(lessonId: Int) {
        AlarmRepository.toggleEnabled(lessonId)
    }
}
