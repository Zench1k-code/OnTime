package com.example.ontime.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AssignmentDao {

    @Query("SELECT * FROM assignments ORDER BY deadline")
    fun getAllAssignments(): Flow<List<AssignmentEntity>>

    @Insert
    suspend fun insert(assignment: AssignmentEntity)

    @Update
    suspend fun update(assignment: AssignmentEntity)

    @Delete
    suspend fun delete(assignment: AssignmentEntity)

    @Query("DELETE FROM assignments WHERE lessonId = :lessonId")
    suspend fun deleteByLessonId(lessonId: Int)
}
