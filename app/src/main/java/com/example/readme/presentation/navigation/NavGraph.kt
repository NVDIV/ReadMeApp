package com.example.readme.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.readme.presentation.screens.AuthScreen
import com.example.readme.presentation.screens.MainScreen
import com.example.readme.presentation.screens.ProfileScreen
import com.example.readme.presentation.screens.DictionaryScreen
import com.example.readme.presentation.screens.LessonScreen
import com.example.readme.presentation.screens.ReadTextScreen

sealed class Screen(val route: String) {
    object Main : Screen("main_screen")
    object Profile : Screen("profile_screen")
    object Dictionary : Screen("dictionary_screen")
    object Lesson : Screen("lesson_screen")
    object ReadText : Screen("read_text_screen")
    object Auth : Screen("auth_screen")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Auth.route) {
        composable(Screen.Main.route) { MainScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.Dictionary.route) { DictionaryScreen(navController) }
        composable(Screen.Lesson.route) { LessonScreen(navController) }
        composable(Screen.ReadText.route) { ReadTextScreen(navController) }
        composable(Screen.Auth.route) { AuthScreen(navController) }
    }
}

