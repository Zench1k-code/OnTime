package com.example.ontime.ui.alarm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ontime.ui.components.AlarmItem

@Composable
fun AlarmScreen(
    onAlarmClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AlarmViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.alarmsByDay.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Add lessons to your schedule to get alarms",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        return
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            NextAlarmCard(uiState.nextAlarm)
        }
        uiState.alarmsByDay.forEach { (day, alarms) ->
            item(key = day) {
                Text(
                    text = day,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            items(alarms, key = { it.lesson.id }) { item ->
                AlarmItem(
                    lesson = item.lesson,
                    alarm = item.alarm,
                    ringTime = item.ringTime,
                    onToggle = { viewModel.toggleAlarm(item.lesson.id) },
                    onClick = { onAlarmClick(item.lesson.id) }
                )
            }
        }
    }
}

@Composable
private fun NextAlarmCard(nextAlarm: NextAlarmUi?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Next alarm",
                style = MaterialTheme.typography.labelLarge
            )
            if (nextAlarm == null) {
                Text(
                    text = "All alarms are off",
                    style = MaterialTheme.typography.titleLarge
                )
            } else {
                Text(
                    text = "${nextAlarm.day} ${nextAlarm.ringTime}",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "${nextAlarm.lessonName} · ${nextAlarm.timeUntil}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
