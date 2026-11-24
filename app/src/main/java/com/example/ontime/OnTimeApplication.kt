package com.example.ontime

import android.app.Application
import com.example.ontime.data.local.AppDatabase
import com.example.ontime.data.repository.LessonRepository
import com.example.ontime.data.repository.SettingsRepository

class OnTimeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val database = AppDatabase.getDatabase(this)
        LessonRepository.init(database.lessonDao())
        LessonRepository.init(database.lessonDao())
        SettingsRepository.init(this)
    }
}