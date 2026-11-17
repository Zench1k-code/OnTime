package com.example.ontime.ui.schedule

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.ontime.data.model.Lesson
import com.example.ontime.data.model.LessonType
import com.example.ontime.data.repository.LessonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddEditLessonViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val lessonId: Int = savedStateHandle["lessonId"] ?: -1
    val isEditMode: Boolean = lessonId != -1

    private val _uiState = MutableStateFlow(LessonFormState())
    val uiState: StateFlow<LessonFormState> = _uiState.asStateFlow()

    init {
        if (isEditMode) {
            LessonRepository.getLessonById(lessonId)?.let { lesson ->
                _uiState.value = LessonFormState(
                    name = lesson.name,
                    type = lesson.type,
                    group = lesson.group,
                    room = lesson.room,
                    dayOfWeek = lesson.dayOfWeek,
                    startTime = lesson.startTime,
                    endTime = lesson.endTime,
                    note = lesson.note
                )
            }
        }
    }

    fun onNameChange(value: String) = _uiState.update { it.copy(name = value, nameError = false) }
    fun onTypeChange(value: LessonType) = _uiState.update { it.copy(type = value) }
    fun onGroupChange(value: String) = _uiState.update { it.copy(group = value) }
    fun onRoomChange(value: String) = _uiState.update { it.copy(room = value) }
    fun onDayChange(value: Int) = _uiState.update { it.copy(dayOfWeek = value) }
    fun onNoteChange(value: String) = _uiState.update { it.copy(note = value) }
    fun onStartTimeChange(value: String) = _uiState.update { it.copy(startTime = value, timeError = false) }
    fun onEndTimeChange(value: String) = _uiState.update { it.copy(endTime = value, timeError = false) }

    fun onTimeSlotSelected(start: String, end: String) =
        _uiState.update { it.copy(startTime = start, endTime = end, timeError = false) }

    fun save(): Boolean {
        val state = _uiState.value
        val nameError = state.name.isBlank()
        val timeError = !isValidTime(state.startTime) ||
                !isValidTime(state.endTime) ||
                state.startTime >= state.endTime

        if (nameError || timeError) {
            _uiState.update { it.copy(nameError = nameError, timeError = timeError) }
            return false
        }

        val lesson = Lesson(
            id = if (isEditMode) lessonId else 0,
            name = state.name.trim(),
            type = state.type,
            group = state.group.trim(),
            room = state.room.trim(),
            dayOfWeek = state.dayOfWeek,
            startTime = state.startTime,
            endTime = state.endTime,
            note = state.note.trim()
        )

        if (isEditMode) LessonRepository.updateLesson(lesson)
        else LessonRepository.addLesson(lesson)
        return true
    }

    private fun isValidTime(time: String): Boolean =
        Regex("^([01]\\d|2[0-3]):[0-5]\\d$").matches(time)
}