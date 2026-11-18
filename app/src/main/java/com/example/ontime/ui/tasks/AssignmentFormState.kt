package com.example.ontime.ui.tasks

data class AssignmentFormState(
    val title: String = "",
    val description: String = "",
    val lessonId: Int? = null,
    val deadline: Long? = null,
    val isDone: Boolean = false,
    val titleError: Boolean = false,
    val deadlineError: Boolean = false
)
