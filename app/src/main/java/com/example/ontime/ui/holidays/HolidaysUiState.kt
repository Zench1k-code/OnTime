package com.example.ontime.ui.holidays

import com.example.ontime.data.remote.HolidayDto

sealed interface HolidaysUiState {
    data object Loading : HolidaysUiState
    data class Success(val holidays: List<HolidayDto>) : HolidaysUiState
    data class Error(val message: String) : HolidaysUiState
    data object Empty : HolidaysUiState
}
