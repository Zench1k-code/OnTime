package com.example.ontime.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ontime.data.repository.AssignmentRepository
import com.example.ontime.data.repository.LessonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class TasksViewModel : ViewModel() {

    private val filter = MutableStateFlow(TaskFilter.ALL)

    val uiState: StateFlow<TasksUiState> =
        combine(
            AssignmentRepository.assignments,
            LessonRepository.lessons,
            filter
        ) { assignments, lessons, currentFilter ->
            val lessonNames = lessons.associate { it.id to it.name }
            val filtered = when (currentFilter) {
                TaskFilter.ALL -> assignments
                TaskFilter.ACTIVE -> assignments.filter { !it.isDone }
                TaskFilter.DONE -> assignments.filter { it.isDone }
            }
            TasksUiState(
                filter = currentFilter,
                // not done first, then the nearest deadline first
                tasks = filtered
                    .sortedWith(compareBy({ it.isDone }, { it.deadline }))
                    .map { TaskItemUi(it, lessonNames[it.lessonId]) },
                activeCount = assignments.count { !it.isDone }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TasksUiState()
        )

    fun selectFilter(newFilter: TaskFilter) {
        filter.value = newFilter
    }

    fun toggleDone(id: Int) {
        AssignmentRepository.toggleDone(id)
    }
}
