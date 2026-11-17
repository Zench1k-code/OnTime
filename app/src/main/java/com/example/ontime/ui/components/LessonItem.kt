package com.example.ontime.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ontime.data.model.Lesson
import com.example.ontime.data.model.LessonType

@Composable
fun LessonItem(
    lesson: Lesson,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = lesson.startTime,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = lesson.endTime,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = lesson.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = buildString {
                        append(lessonTypeLabel(lesson.type))
                        if (lesson.group.isNotBlank()) append(" · ${lesson.group}")
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
                if (lesson.room.isNotBlank()) {
                    Text(
                        text = "Room ${lesson.room}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

fun lessonTypeLabel(type: LessonType): String = when (type) {
    LessonType.LECTURE -> "Lecture"
    LessonType.PRACTICE -> "Practice"
    LessonType.LAB -> "Lab"
    LessonType.OTHER -> "Lesson"
}