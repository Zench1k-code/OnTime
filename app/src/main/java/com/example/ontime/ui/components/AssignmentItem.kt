package com.example.ontime.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ontime.data.model.Assignment
import com.example.ontime.util.deadlineLabel
import com.example.ontime.util.isOverdue

@Composable
fun AssignmentItem(
    assignment: Assignment,
    onCheckedChange: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    lessonName: String? = null
) {
    val overdue = !assignment.isDone && isOverdue(assignment.deadline)

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(start = 4.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = assignment.isDone,
                onCheckedChange = { onCheckedChange() }
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = assignment.title,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (assignment.isDone) TextDecoration.LineThrough else null,
                    color = if (assignment.isDone) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (lessonName != null) {
                    Text(
                        text = lessonName,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(
                    text = if (assignment.isDone) "Done" else deadlineLabel(assignment.deadline),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (overdue) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}
