package com.example.ontime.data.model

// Every lesson has one alarm. It rings [minutesBefore] minutes before the lesson starts.
data class LessonAlarm(
    val lessonId: Int,
    val minutesBefore: Int = 15,
    val isEnabled: Boolean = true
)
