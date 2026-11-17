package com.example.ontime.ui.schedule

import com.example.ontime.data.model.LessonType

data class LessonFormState(
    val name: String = "",
    val type: LessonType = LessonType.LECTURE,
    val group: String = "",
    val room: String = "",
    val dayOfWeek: Int = 1,
    val startTime: String = "",
    val endTime: String = "",
    val note: String = "",
    val nameError: Boolean = false,
    val timeError: Boolean = false
)