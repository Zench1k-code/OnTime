package com.example.ontime.data.repository

import com.example.ontime.data.model.Assignment
import com.example.ontime.util.daysFromToday
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object AssignmentRepository {

    // Sample data is linked to the sample lessons in LessonRepository (ids 1-3)
    private val _assignments = MutableStateFlow(
        listOf(
            Assignment(1, "Lab 3 report", "Upload the report to the portal", lessonId = 2, deadline = daysFromToday(2)),
            Assignment(2, "Read chapter 5", lessonId = 3, deadline = daysFromToday(1)),
            Assignment(3, "Project proposal", "Agree on the topic with the team", lessonId = 1, deadline = daysFromToday(5)),
            Assignment(4, "Quiz preparation", lessonId = 3, deadline = daysFromToday(-1)),
            Assignment(5, "Install Android Studio", lessonId = 2, deadline = daysFromToday(-3), isDone = true)
        )
    )
    val assignments: StateFlow<List<Assignment>> = _assignments.asStateFlow()

    private var nextId = 6

    fun getAssignmentById(id: Int): Assignment? =
        _assignments.value.find { it.id == id }

    fun addAssignment(assignment: Assignment) {
        _assignments.update { it + assignment.copy(id = nextId++) }
    }

    fun updateAssignment(assignment: Assignment) {
        _assignments.update { list -> list.map { if (it.id == assignment.id) assignment else it } }
    }

    fun deleteAssignment(id: Int) {
        _assignments.update { list -> list.filterNot { it.id == id } }
    }

    fun toggleDone(id: Int) {
        _assignments.update { list ->
            list.map { if (it.id == id) it.copy(isDone = !it.isDone) else it }
        }
    }
}
