package com.example.readme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.readme.presentation.navigation.NavGraph
import com.example.readme.presentation.viewmodels.AuthViewModel
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
    val viewModel: AuthViewModel = viewModel()
    val isLoggedIn by remember { mutableStateOf(viewModel.isLoggedIn) }
    NavGraph(navController, isLoggedIn)
}

