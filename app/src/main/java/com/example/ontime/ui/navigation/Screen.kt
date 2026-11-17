package com.example.ontime.ui.navigation

sealed class Screen(val route: String) {

    data object Schedule : Screen("schedule")
    data object Tasks : Screen("tasks")
    data object Alarm : Screen("alarm")

    data object LessonDetail : Screen("lesson/{lessonId}") {
        fun createRoute(lessonId: Int) = "lesson/$lessonId"
    }

    data object AddEditLesson : Screen("lesson_edit?lessonId={lessonId}") {
        fun createRoute(lessonId: Int? = null) =
            if (lessonId == null) "lesson_edit" else "lesson_edit?lessonId=$lessonId"
    }
}