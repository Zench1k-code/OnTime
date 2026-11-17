package com.example.ontime.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ontime.data.repository.LessonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar

class ScheduleViewModel : ViewModel() {

    private val selectedDay = MutableStateFlow(todayDayOfWeek())

    val uiState: StateFlow<ScheduleUiState> =
        combine(LessonRepository.lessons, selectedDay) { lessons, day ->
            ScheduleUiState(
                selectedDay = day,
                lessons = lessons
                    .filter { it.dayOfWeek == day }
                    .sortedBy { it.startTime }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ScheduleUiState(selectedDay = selectedDay.value)
        )

    fun selectDay(day: Int) {
        selectedDay.value = day
    }

    fun deleteLesson(id: Int) {
        LessonRepository.deleteLesson(id)
    }

    private fun todayDayOfWeek(): Int {
        val day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        return if (day == Calendar.SUNDAY) 7 else day - 1
    }
}