package com.example.readme.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.readme.presentation.components.DrawerLayout

@Composable
fun ReadTextScreen(navController: NavHostController, ) {
    DrawerLayout(navController) { modifier ->
        ReadTextScreenContent(modifier) // Pass padding modifier
    }
}

@Composable
fun ReadTextScreenContent(modifier: Modifier) {}