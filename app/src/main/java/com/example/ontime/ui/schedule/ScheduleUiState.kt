package com.example.ontime.ui.schedule

import com.example.ontime.data.model.Lesson

data class ScheduleUiState(
    val selectedDay: Int = 1,
    val lessons: List<Lesson> = emptyList()
)