package com.example.ontime.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ontime.data.model.Lesson
import com.example.ontime.data.model.LessonAlarm

@Composable
fun AlarmItem(
    lesson: Lesson,
    alarm: LessonAlarm,
    ringTime: String,
    onToggle: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textColor = if (alarm.isEnabled) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = ringTime,
                    style = MaterialTheme.typography.headlineMedium,
                    color = textColor
                )
                Text(
                    text = "${lesson.name} · ${lesson.startTime}",
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor
                )
                Text(
                    text = "${alarm.minutesBefore} min before the lesson",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Switch(
                checked = alarm.isEnabled,
                onCheckedChange = { onToggle() }
            )
        }
    }
}
