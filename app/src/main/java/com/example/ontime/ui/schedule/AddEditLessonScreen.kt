package com.example.ontime.ui.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ontime.data.model.LessonType
import com.example.ontime.ui.components.AppTopBar
import com.example.ontime.ui.components.DaySelector
import com.example.ontime.ui.components.lessonTypeLabel

private val timeSlots = listOf(
    "08:30" to "09:20",
    "09:30" to "10:20",
    "10:30" to "11:20",
    "11:30" to "12:20",
    "12:30" to "13:20",
    "13:30" to "14:20",
    "14:30" to "15:20",
    "15:30" to "16:20",
    "16:30" to "17:20"
)

@Composable
fun AddEditLessonScreen(
    onBackClick: () -> Unit,
    onSaved: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddEditLessonViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            AppTopBar(
                title = if (viewModel.isEditMode) "Edit lesson" else "Add lesson",
                canNavigateBack = true,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Subject") },
                placeholder = { Text("CSS 251 or Mathematics") },
                isError = state.nameError,
                supportingText = {
                    if (state.nameError) Text("Enter a subject name")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            SectionLabel("Type")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(LessonType.entries) { type ->
                    FilterChip(
                        selected = state.type == type,
                        onClick = { viewModel.onTypeChange(type) },
                        label = { Text(lessonTypeLabel(type)) }
                    )
                }
            }

            SectionLabel("Day")
            DaySelector(
                selectedDay = state.dayOfWeek,
                onDaySelected = viewModel::onDayChange
            )

            SectionLabel("Time")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(timeSlots) { (start, end) ->
                    FilterChip(
                        selected = state.startTime == start && state.endTime == end,
                        onClick = { viewModel.onTimeSlotSelected(start, end) },
                        label = { Text("$start–$end") }
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = state.startTime,
                    onValueChange = viewModel::onStartTimeChange,
                    label = { Text("Start") },
                    placeholder = { Text("09:00") },
                    isError = state.timeError,
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = state.endTime,
                    onValueChange = viewModel::onEndTimeChange,
                    label = { Text("End") },
                    placeholder = { Text("10:30") },
                    isError = state.timeError,
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
            }
            if (state.timeError) {
                Text(
                    text = "Use HH:MM format, and the end must be after the start",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = state.room,
                    onValueChange = viewModel::onRoomChange,
                    label = { Text("Room") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = state.group,
                    onValueChange = viewModel::onGroupChange,
                    label = { Text("Group") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = state.note,
                onValueChange = viewModel::onNoteChange,
                label = { Text("Teacher or class") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { if (viewModel.save()) onSaved() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (viewModel.isEditMode) "Save changes" else "Add lesson")
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}