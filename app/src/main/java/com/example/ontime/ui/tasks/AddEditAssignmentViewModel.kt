package com.example.ontime.ui.tasks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.ontime.data.model.Assignment
import com.example.ontime.data.model.Lesson
import com.example.ontime.data.repository.AssignmentRepository
import com.example.ontime.data.repository.LessonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddEditAssignmentViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    // Both arguments come from the navigation route, -1 means "not passed"
    private val assignmentId: Int = savedStateHandle["assignmentId"] ?: -1
    private val presetLessonId: Int = savedStateHandle["lessonId"] ?: -1
    val isEditMode: Boolean = assignmentId != -1

    /** Lessons shown in the lesson picker. */
    val lessons: StateFlow<List<Lesson>> = LessonRepository.lessons

    private val _uiState = MutableStateFlow(
        AssignmentFormState(lessonId = presetLessonId.takeIf { it != -1 })
    )
    val uiState: StateFlow<AssignmentFormState> = _uiState.asStateFlow()

    init {
        if (isEditMode) {
            AssignmentRepository.getAssignmentById(assignmentId)?.let { assignment ->
                _uiState.value = AssignmentFormState(
                    title = assignment.title,
                    description = assignment.description,
                    lessonId = assignment.lessonId,
                    deadline = assignment.deadline,
                    isDone = assignment.isDone
                )
            }
        }
    }

    fun onTitleChange(value: String) = _uiState.update { it.copy(title = value, titleError = false) }
    fun onDescriptionChange(value: String) = _uiState.update { it.copy(description = value) }
    fun onDeadlineChange(value: Long?) = _uiState.update { it.copy(deadline = value, deadlineError = false) }

    /** Tapping the selected lesson again removes the link. */
    fun onLessonSelected(id: Int) = _uiState.update {
        it.copy(lessonId = if (it.lessonId == id) null else id)
    }

    fun save(): Boolean {
        val state = _uiState.value
        val deadline = state.deadline
        val titleError = state.title.isBlank()
        val deadlineError = deadline == null

        if (titleError || deadline == null) {
            _uiState.update { it.copy(titleError = titleError, deadlineError = deadlineError) }
            return false
        }

        val assignment = Assignment(
            id = if (isEditMode) assignmentId else 0,
            title = state.title.trim(),
            description = state.description.trim(),
            lessonId = state.lessonId,
            deadline = deadline,
            isDone = state.isDone
        )

        if (isEditMode) AssignmentRepository.updateAssignment(assignment)
        else AssignmentRepository.addAssignment(assignment)
        return true
    }

    fun delete() {
        if (isEditMode) AssignmentRepository.deleteAssignment(assignmentId)
    }
}
