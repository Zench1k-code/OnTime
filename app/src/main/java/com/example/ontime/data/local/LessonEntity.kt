package com.example.ontime.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ontime.data.model.Lesson
import com.example.ontime.data.model.LessonType

@Entity(tableName = "lessons")
data class LessonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String,
    @ColumnInfo(name = "lesson_group") val group: String,
    val room: String,
    val dayOfWeek: Int,
    val startTime: String,
    val endTime: String,
    val note: String
)

fun LessonEntity.toLesson(): Lesson = Lesson(
    id = id,
    name = name,
    type = runCatching { LessonType.valueOf(type) }.getOrDefault(LessonType.OTHER),
    group = group,
    room = room,
    dayOfWeek = dayOfWeek,
    startTime = startTime,
    endTime = endTime,
    note = note
)

fun Lesson.toEntity(): LessonEntity = LessonEntity(
    id = id,
    name = name,
    type = type.name,
    group = group,
    room = room,
    dayOfWeek = dayOfWeek,
    startTime = startTime,
    endTime = endTime,
    note = note
)