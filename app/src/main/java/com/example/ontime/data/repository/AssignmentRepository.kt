package com.example.ontime.data.repository

import com.example.ontime.data.local.AssignmentDao
import com.example.ontime.data.local.toAssignment
import com.example.ontime.data.local.toEntity
import com.example.ontime.data.model.Assignment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object AssignmentRepository {

    private lateinit var dao: AssignmentDao
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _assignments = MutableStateFlow<List<Assignment>>(emptyList())
    val assignments: StateFlow<List<Assignment>> = _assignments.asStateFlow()

    fun init(assignmentDao: AssignmentDao) {
        dao = assignmentDao
        scope.launch {
            dao.getAllAssignments().collect { entities ->
                _assignments.value = entities.map { it.toAssignment() }
            }
        }
    }

    fun getAssignmentById(id: Int): Assignment? =
        _assignments.value.find { it.id == id }

    fun addAssignment(assignment: Assignment) {
        scope.launch { dao.insert(assignment.toEntity().copy(id = 0)) }
    }

    fun updateAssignment(assignment: Assignment) {
        scope.launch { dao.update(assignment.toEntity()) }
    }

    fun deleteAssignment(id: Int) {
        val assignment = getAssignmentById(id) ?: return
        scope.launch { dao.delete(assignment.toEntity()) }
    }

    fun deleteAssignmentsOfLesson(lessonId: Int) {
        scope.launch { dao.deleteByLessonId(lessonId) }
    }

    fun toggleDone(id: Int) {
        val assignment = getAssignmentById(id) ?: return
        updateAssignment(assignment.copy(isDone = !assignment.isDone))
    }
}
