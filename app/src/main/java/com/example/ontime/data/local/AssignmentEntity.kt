package com.example.ontime.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ontime.data.model.Assignment

@Entity(tableName = "assignments")
data class AssignmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val lessonId: Int?,
    val deadline: Long,
    val isDone: Boolean
)

fun AssignmentEntity.toAssignment(): Assignment = Assignment(
    id = id,
    title = title,
    description = description,
    lessonId = lessonId,
    deadline = deadline,
    isDone = isDone
)

fun Assignment.toEntity(): AssignmentEntity = AssignmentEntity(
    id = id,
    title = title,
    description = description,
    lessonId = lessonId,
    deadline = deadline,
    isDone = isDone
)
