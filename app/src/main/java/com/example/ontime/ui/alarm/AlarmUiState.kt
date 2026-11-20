package com.example.ontime.ui.alarm

import com.example.ontime.data.model.Lesson
import com.example.ontime.data.model.LessonAlarm

data class AlarmItemUi(
    val lesson: Lesson,
    val alarm: LessonAlarm,
    val ringTime: String          // "08:45"
)

data class NextAlarmUi(
    val lessonName: String,
    val day: String,              // "Monday"
    val ringTime: String,         // "08:45"
    val timeUntil: String         // "in 2 h 10 min"
)

data class AlarmUiState(
    val alarmsByDay: Map<String, List<AlarmItemUi>> = emptyMap(),
    val nextAlarm: NextAlarmUi? = null
)
