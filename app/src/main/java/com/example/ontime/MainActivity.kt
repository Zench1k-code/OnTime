package com.example.ontime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ontime.ui.schedule.ScheduleScreen
import com.example.ontime.ui.theme.OnTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnTimeTheme {
                ScheduleScreen(
                    onLessonClick = { },
                    onAddLessonClick = { }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleScreenPreview() {
    OnTimeTheme {
        ScheduleScreen(
            onLessonClick = { },
            onAddLessonClick = { }
        )
    }
}