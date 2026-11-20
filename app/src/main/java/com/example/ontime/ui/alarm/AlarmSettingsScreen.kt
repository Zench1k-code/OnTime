package com.example.ontime.ui.alarm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ontime.data.model.Lesson
import com.example.ontime.ui.components.AppTopBar
import com.example.ontime.util.dayNames
import com.example.ontime.util.ringTime

private val quickMinutes = listOf(5, 10, 15, 30, 60)

@Composable
fun AlarmSettingsScreen(
    onBackClick: () -> Unit,
    onSaved: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AlarmSettingsViewModel = viewModel()
) {
    val alarm by viewModel.alarm.collectAsState()
    val lesson = viewModel.lesson

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            AppTopBar(
                title = "Alarm",
                canNavigateBack = true,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        if (lesson == null) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Lesson not found")
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LessonInfoCard(lesson)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Alarm is on",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = alarm.isEnabled,
                    onCheckedChange = viewModel::setEnabled
                )
            }

            Text(
                text = "Ring before the lesson",
                style = MaterialTheme.typography.titleMedium
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                quickMinutes.forEach { minutes ->
                    FilterChip(
                        selected = alarm.minutesBefore == minutes,
                        onClick = { viewModel.setMinutesBefore(minutes) },
                        label = { Text("$minutes") }
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FilledTonalButton(
                    onClick = { viewModel.setMinutesBefore(alarm.minutesBefore - 1) },
                    enabled = alarm.minutesBefore > MIN_MINUTES_BEFORE
                ) { Text("−") }
                Text(
                    text = "${alarm.minutesBefore} min",
                    style = MaterialTheme.typography.headlineSmall
                )
                FilledTonalButton(
                    onClick = { viewModel.setMinutesBefore(alarm.minutesBefore + 1) },
                    enabled = alarm.minutesBefore < MAX_MINUTES_BEFORE
                ) { Text("+") }
            }

            Text(
                text = if (alarm.isEnabled) {
                    "Will ring at ${ringTime(lesson.startTime, alarm.minutesBefore)}"
                } else {
                    "Alarm is off"
                },
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Button(
                onClick = {
                    viewModel.save()
                    onSaved()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}

@Composable
private fun LessonInfoCard(lesson: Lesson) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = lesson.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "${dayNames[lesson.dayOfWeek - 1]} · ${lesson.startTime} - ${lesson.endTime}",
                style = MaterialTheme.typography.bodyMedium
            )
            if (lesson.room.isNotBlank()) {
                Text(
                    text = "Room ${lesson.room}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
