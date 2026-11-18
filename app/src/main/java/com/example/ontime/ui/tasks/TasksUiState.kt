package com.example.ontime.ui.tasks

import com.example.ontime.data.model.Assignment

enum class TaskFilter(val label: String) {
    ALL("All"),
    ACTIVE("Active"),
    DONE("Done")
}

/** An assignment together with the name of its lesson, ready to show in the list. */
data class TaskItemUi(
    val assignment: Assignment,
    val lessonName: String?
)

data class TasksUiState(
    val filter: TaskFilter = TaskFilter.ALL,
    val tasks: List<TaskItemUi> = emptyList(),
    val activeCount: Int = 0
)
