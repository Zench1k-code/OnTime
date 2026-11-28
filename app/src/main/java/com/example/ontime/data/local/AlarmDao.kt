package com.example.ontime.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Query("SELECT * FROM alarms")
    fun getAllAlarms(): Flow<List<LessonAlarmEntity>>

    // Insert a new alarm or replace the saved one of the same lesson
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(alarm: LessonAlarmEntity)

    @Query("DELETE FROM alarms WHERE lessonId = :lessonId")
    suspend fun deleteByLessonId(lessonId: Int)
}
