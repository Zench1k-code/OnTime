package com.example.ontime.data.repository

import com.example.ontime.data.remote.HolidayApi
import com.example.ontime.data.remote.HolidayDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HolidayRepository {

    private const val BASE_URL = "https://nagerholidays.com/"

    private val api: HolidayApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HolidayApi::class.java)

    suspend fun getHolidays(year: Int, countryCode: String = "KZ"): List<HolidayDto> =
        api.getPublicHolidays(year, countryCode)
}
