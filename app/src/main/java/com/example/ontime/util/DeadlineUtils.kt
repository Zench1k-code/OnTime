package com.example.ontime.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

// Deadlines are stored as UTC midnight in milliseconds,
// the same format the Material 3 DatePicker returns.

private val utc: TimeZone = TimeZone.getTimeZone("UTC")
private const val DAY_MILLIS = 24 * 60 * 60 * 1000L

/** Today's date (in the user's time zone) as UTC midnight millis. */
fun todayMillis(): Long {
    val local = Calendar.getInstance()
    return Calendar.getInstance(utc).apply {
        clear()
        set(
            local.get(Calendar.YEAR),
            local.get(Calendar.MONTH),
            local.get(Calendar.DAY_OF_MONTH)
        )
    }.timeInMillis
}

fun daysFromToday(days: Int): Long = todayMillis() + days * DAY_MILLIS

fun isOverdue(deadline: Long): Boolean = deadline < todayMillis()

/** "25 Nov 2026" */
fun formatDeadline(deadline: Long): String =
    SimpleDateFormat("d MMM yyyy", Locale.ENGLISH)
        .apply { timeZone = utc }
        .format(deadline)

/** "Today", "Tomorrow", "Overdue · 20 Nov 2026" or "Due 25 Nov 2026" */
fun deadlineLabel(deadline: Long): String {
    val today = todayMillis()
    return when {
        deadline < today -> "Overdue · ${formatDeadline(deadline)}"
        deadline == today -> "Due today"
        deadline == today + DAY_MILLIS -> "Due tomorrow"
        else -> "Due ${formatDeadline(deadline)}"
    }
}
