package com.example.ontime.ui.holidays

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ontime.data.repository.HolidayRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class HolidaysViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<HolidaysUiState>(HolidaysUiState.Loading)
    val uiState: StateFlow<HolidaysUiState> = _uiState.asStateFlow()

    val year: Int = Calendar.getInstance().get(Calendar.YEAR)

    init {
        loadHolidays()
    }

    fun loadHolidays() {
        _uiState.value = HolidaysUiState.Loading
        viewModelScope.launch {
            _uiState.value = try {
                val holidays = HolidayRepository.getHolidays(year)
                if (holidays.isEmpty()) HolidaysUiState.Empty
                else HolidaysUiState.Success(holidays)
            } catch (e: Exception) {
                HolidaysUiState.Error("Could not load holidays. Check your internet connection.")
            }
        }
    }
}
