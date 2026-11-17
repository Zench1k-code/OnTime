package com.example.ontime.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ontime.ui.schedule.ScheduleScreen

@Composable
fun OnTimeNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Schedule.route,
        modifier = modifier
    ) {
        composable(Screen.Schedule.route) {
            ScheduleScreen(
                onLessonClick = { lessonId ->
                    navController.navigate(Screen.LessonDetail.createRoute(lessonId))
                },
                onAddLessonClick = {
                    navController.navigate(Screen.AddEditLesson.createRoute())
                }
            )
        }

        composable(Screen.Tasks.route) {
            PlaceholderScreen("Tasks")
        }

        composable(Screen.Alarm.route) {
            PlaceholderScreen("Alarm")
        }

        composable(
            route = Screen.LessonDetail.route,
            arguments = listOf(navArgument("lessonId") { type = NavType.IntType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments?.getInt("lessonId") ?: 0
            PlaceholderScreen("Lesson details, id = $lessonId")
        }

        composable(
            route = Screen.AddEditLesson.route,
            arguments = listOf(
                navArgument("lessonId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments?.getInt("lessonId") ?: -1
            PlaceholderScreen(if (lessonId == -1) "Add lesson" else "Edit lesson $lessonId")
        }
    }
}

@Composable
private fun PlaceholderScreen(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}