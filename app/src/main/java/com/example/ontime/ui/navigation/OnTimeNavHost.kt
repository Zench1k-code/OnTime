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
import com.example.ontime.ui.schedule.AddEditLessonScreen
import com.example.ontime.ui.schedule.LessonDetailScreen
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
        ) {
            LessonDetailScreen(
                onBackClick = { navController.navigateUp() },
                onEditClick = { lessonId ->
                    navController.navigate(Screen.AddEditLesson.createRoute(lessonId))
                }
            )
        }

        composable(
            route = Screen.AddEditLesson.route,
            arguments = listOf(
                navArgument("lessonId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditLessonScreen(
                onBackClick = { navController.navigateUp() },
                onSaved = { navController.navigateUp() }
            )
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