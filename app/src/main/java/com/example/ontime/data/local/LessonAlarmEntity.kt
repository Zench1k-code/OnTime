package com.example.ontime.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ontime.data.model.LessonAlarm

// Every lesson has only one alarm, so lessonId is the primary key.
@Entity(tableName = "alarms")
data class LessonAlarmEntity(
    @PrimaryKey val lessonId: Int,
    val minutesBefore: Int,
    val isEnabled: Boolean
)

fun LessonAlarmEntity.toLessonAlarm(): LessonAlarm = LessonAlarm(
    lessonId = lessonId,
    minutesBefore = minutesBefore,
    isEnabled = isEnabled
)

fun LessonAlarm.toEntity(): LessonAlarmEntity = LessonAlarmEntity(
    lessonId = lessonId,
    minutesBefore = minutesBefore,
    isEnabled = isEnabled
)
