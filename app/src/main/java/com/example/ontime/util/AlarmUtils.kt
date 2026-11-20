package com.example.ontime.util

import java.util.Calendar

private const val MINUTES_IN_DAY = 24 * 60
private const val MINUTES_IN_WEEK = 7 * MINUTES_IN_DAY

val dayNames = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

/** "15:30" -> 930 */
fun toMinutes(time: String): Int {
    val (hours, minutes) = time.split(":").map { it.trim().toInt() }
    return hours * 60 + minutes
}

/** Ring time of an alarm, e.g. lesson at "09:00" and 15 minutes before -> "08:45" */
fun ringTime(startTime: String, minutesBefore: Int): String {
    val total = Math.floorMod(toMinutes(startTime) - minutesBefore, MINUTES_IN_DAY)
    return "%02d:%02d".format(total / 60, total % 60)
}

/** Minutes from now until the alarm rings next time (the schedule repeats every week). */
fun minutesUntilRing(dayOfWeek: Int, startTime: String, minutesBefore: Int): Int {
    val now = Calendar.getInstance()
    // Calendar: Sunday = 1 ... Saturday = 7, our lessons: Monday = 1 ... Sunday = 7
    val today = (now.get(Calendar.DAY_OF_WEEK) + 5) % 7 + 1
    val nowInWeek = (today - 1) * MINUTES_IN_DAY +
        now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE)
    val ringInWeek = (dayOfWeek - 1) * MINUTES_IN_DAY + toMinutes(startTime) - minutesBefore
    return Math.floorMod(ringInWeek - nowInWeek, MINUTES_IN_WEEK)
}

/** 15 -> "in 15 min", 130 -> "in 2 h 10 min", 1600 -> "in 1 d 2 h" */
fun timeUntilLabel(minutes: Int): String {
    val days = minutes / MINUTES_IN_DAY
    val hours = minutes % MINUTES_IN_DAY / 60
    val mins = minutes % 60
    return when {
        days > 0 -> "in $days d $hours h"
        hours > 0 -> "in $hours h $mins min"
        else -> "in $mins min"
    }
}
