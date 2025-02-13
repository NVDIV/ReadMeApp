package com.example.readme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.compose.rememberNavController
import com.example.readme.presentation.navigation.NavGraph
import com.example.readme.ui.theme.ReadMeTheme
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadMeTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavGraph(navController = navController)
}

