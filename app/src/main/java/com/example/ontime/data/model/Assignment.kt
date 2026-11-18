package com.example.ontime.data.model

data class Assignment(
    val id: Int,
    val title: String,             // "Lab 3 report"
    val description: String = "",
    val lessonId: Int? = null,     // related lesson, null if not linked to a lesson
    val deadline: Long,            // deadline date in milliseconds (UTC midnight)
    val isDone: Boolean = false
)
