package com.example.ontime.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao {

    @Query("SELECT * FROM lessons ORDER BY dayOfWeek, startTime")
    fun getAllLessons(): Flow<List<LessonEntity>>

    @Insert
    suspend fun insert(lesson: LessonEntity)

    @Update
    suspend fun update(lesson: LessonEntity)

    @Delete
    suspend fun delete(lesson: LessonEntity)
}