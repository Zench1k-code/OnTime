package com.example.ontime.ui.navigation

sealed class Screen(val route: String) {

    data object Schedule : Screen("schedule")
    data object Tasks : Screen("tasks")
    data object Alarm : Screen("alarm")

    data object Settings : Screen("settings")

    data object LessonDetail : Screen("lesson/{lessonId}") {
        fun createRoute(lessonId: Int) = "lesson/$lessonId"
    }

    data object AlarmSettings : Screen("alarm/{lessonId}") {
        fun createRoute(lessonId: Int) = "alarm/$lessonId"
    }

    data object AddEditLesson : Screen("lesson_edit?lessonId={lessonId}") {
        fun createRoute(lessonId: Int? = null) =
            if (lessonId == null) "lesson_edit" else "lesson_edit?lessonId=$lessonId"
    }

    // Both arguments are optional:
    // assignmentId -> edit an existing assignment
    // lessonId     -> new assignment with this lesson already selected
    data object AddEditAssignment :
        Screen("assignment_edit?assignmentId={assignmentId}&lessonId={lessonId}") {
        fun createRoute(assignmentId: Int? = null, lessonId: Int? = null) =
            "assignment_edit?assignmentId=${assignmentId ?: -1}&lessonId=${lessonId ?: -1}"
    }
}