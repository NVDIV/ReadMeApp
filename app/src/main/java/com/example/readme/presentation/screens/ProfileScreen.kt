package com.example.readme.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.readme.presentation.components.DrawerContent
import com.example.readme.presentation.components.DrawerLayout
import com.example.readme.presentation.components.TopBar
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavHostController) {
    DrawerLayout(navController) { modifier ->
        ProfileScreenContent(modifier) // Pass padding modifier
    }
}

@Composable
fun ProfileScreenContent(modifier: Modifier) {}
