package com.example.ontime.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ontime.ui.alarm.AlarmScreen
import com.example.ontime.ui.alarm.AlarmSettingsScreen
import com.example.ontime.ui.schedule.AddEditLessonScreen
import com.example.ontime.ui.schedule.LessonDetailScreen
import com.example.ontime.ui.schedule.ScheduleScreen
import com.example.ontime.ui.settings.SettingsScreen
import com.example.ontime.ui.tasks.AddEditAssignmentScreen
import com.example.ontime.ui.tasks.TasksScreen

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
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(Screen.Tasks.route) {
            TasksScreen(
                onTaskClick = { assignmentId ->
                    navController.navigate(Screen.AddEditAssignment.createRoute(assignmentId = assignmentId))
                },
                onAddTaskClick = {
                    navController.navigate(Screen.AddEditAssignment.createRoute())
                }
            )
        }

        composable(Screen.Alarm.route) {
            AlarmScreen(
                onAlarmClick = { lessonId ->
                    navController.navigate(Screen.AlarmSettings.createRoute(lessonId))
                }
            )
        }

        composable(
            route = Screen.AlarmSettings.route,
            arguments = listOf(navArgument("lessonId") { type = NavType.IntType })
        ) {
            AlarmSettingsScreen(
                onBackClick = { navController.navigateUp() },
                onSaved = { navController.navigateUp() }
            )
        }

        composable(
            route = Screen.LessonDetail.route,
            arguments = listOf(navArgument("lessonId") { type = NavType.IntType })
        ) {
            LessonDetailScreen(
                onBackClick = { navController.navigateUp() },
                onEditClick = { lessonId ->
                    navController.navigate(Screen.AddEditLesson.createRoute(lessonId))
                },
                onAssignmentClick = { assignmentId ->
                    navController.navigate(Screen.AddEditAssignment.createRoute(assignmentId = assignmentId))
                },
                onAddAssignmentClick = { lessonId ->
                    navController.navigate(Screen.AddEditAssignment.createRoute(lessonId = lessonId))
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

        composable(
            route = Screen.AddEditAssignment.route,
            arguments = listOf(
                navArgument("assignmentId") {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument("lessonId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditAssignmentScreen(
                onBackClick = { navController.navigateUp() },
                onSaved = { navController.navigateUp() }
            )
        }
    }
}
