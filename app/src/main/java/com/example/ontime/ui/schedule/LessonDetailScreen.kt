package com.example.ontime.ui.schedule

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ontime.data.model.Assignment
import com.example.ontime.data.model.Lesson
import com.example.ontime.ui.components.AppTopBar
import com.example.ontime.ui.components.AssignmentItem
import com.example.ontime.ui.components.lessonTypeLabel

@Composable
fun LessonDetailScreen(
    onBackClick: () -> Unit,
    onEditClick: (Int) -> Unit,
    onAssignmentClick: (Int) -> Unit,
    onAddAssignmentClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LessonDetailViewModel = viewModel()
) {
    val lesson by viewModel.lesson.collectAsState()
    val assignments by viewModel.assignments.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            AppTopBar(
                title = lesson?.name ?: "Lesson",
                canNavigateBack = true,
                onBackClick = onBackClick,
                actions = {
                    lesson?.let { current ->
                        IconButton(onClick = { onEditClick(current.id) }) {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit lesson")
                        }
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Delete lesson")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        val current = lesson
        if (current == null) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Lesson not found")
            }
        } else {
            LessonDetailContent(
                lesson = current,
                assignments = assignments,
                onAssignmentClick = onAssignmentClick,
                onAssignmentCheckedChange = viewModel::toggleAssignmentDone,
                onAddAssignmentClick = { onAddAssignmentClick(current.id) },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete lesson?") },
            text = { Text("This lesson will be removed from your schedule.") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    viewModel.deleteLesson()
                    onBackClick()
                }) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
private fun LessonDetailContent(
    lesson: Lesson,
    assignments: List<Assignment>,
    onAssignmentClick: (Int) -> Unit,
    onAssignmentCheckedChange: (Int) -> Unit,
    onAddAssignmentClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(lesson.name, style = MaterialTheme.typography.headlineMedium)
        Text(
            text = lessonTypeLabel(lesson.type),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DetailRow("Day", dayFullName(lesson.dayOfWeek))
                DetailRow("Time", "${lesson.startTime} – ${lesson.endTime}")
                if (lesson.room.isNotBlank()) DetailRow("Room", lesson.room)
                if (lesson.group.isNotBlank()) DetailRow("Group", lesson.group)
                if (lesson.note.isNotBlank()) DetailRow("Note", lesson.note)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Assignments",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = onAddAssignmentClick) {
                Icon(Icons.Filled.Add, contentDescription = null)
                Text("Add")
            }
        }
        if (assignments.isEmpty()) {
            Text(
                text = "No assignments for this lesson",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            assignments.forEach { assignment ->
                AssignmentItem(
                    assignment = assignment,
                    onCheckedChange = { onAssignmentCheckedChange(assignment.id) },
                    onClick = { onAssignmentClick(assignment.id) }
                )
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}

private fun dayFullName(day: Int): String = when (day) {
    1 -> "Monday"
    2 -> "Tuesday"
    3 -> "Wednesday"
    4 -> "Thursday"
    5 -> "Friday"
    6 -> "Saturday"
    else -> "Sunday"
}