package com.example.ontime.data.remote

// One public holiday from the Nager.Date API
data class HolidayDto(
    val date: String,       // "2026-03-08"
    val localName: String,  // name in the local language
    val name: String        // name in English
)
