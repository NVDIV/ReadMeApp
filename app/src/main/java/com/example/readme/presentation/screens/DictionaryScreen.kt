package com.example.readme.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.readme.presentation.components.DrawerLayout

@Composable
fun DictionaryScreen(
    navController: NavHostController,
) {
    DrawerLayout(navController) { modifier ->
        ProfileScreenContent(modifier) // Pass padding modifier
    }
}