package com.example.ontime.data.model

enum class LessonType { LECTURE, PRACTICE, LAB, OTHER }

data class Lesson(
    val id: Int,
    val name: String,              // "CSS 251" or "Mathematics"
    val type: LessonType = LessonType.OTHER,
    val group: String = "",        // "14-L", "9B"
    val room: String = "",         // "VR 2"
    val dayOfWeek: Int,            // 1 = Monday ... 7 = Sunday
    val startTime: String,         // "15:30"
    val endTime: String,           // "17:20"
    val note: String = ""          // teacher name for students, class for teachers
)