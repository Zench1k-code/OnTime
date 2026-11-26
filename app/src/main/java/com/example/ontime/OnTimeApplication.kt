package com.example.ontime

import android.app.Application
import com.example.ontime.data.local.AppDatabase
import com.example.ontime.data.repository.AlarmRepository
import com.example.ontime.data.repository.AssignmentRepository
import com.example.ontime.data.repository.LessonRepository
import com.example.ontime.data.repository.SettingsRepository

class OnTimeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val database = AppDatabase.getDatabase(this)
        LessonRepository.init(database.lessonDao())
        AssignmentRepository.init(database.assignmentDao())
        AlarmRepository.init(database.alarmDao())
        SettingsRepository.init(this)
    }
}
