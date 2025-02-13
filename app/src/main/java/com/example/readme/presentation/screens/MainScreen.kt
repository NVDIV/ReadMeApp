package com.example.readme.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.readme.R
import com.example.readme.presentation.components.ActionButton
import com.example.readme.presentation.components.DrawerLayout

@Composable
fun MainScreen(
    navController: NavHostController
) {
    DrawerLayout(navController) { modifier ->
        MainScreenContent(modifier, navController) // Pass padding modifier
    }
}

@Composable
fun MainScreenContent(modifier: Modifier = Modifier, navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Hello! Welcome to Readme!",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            if (!isLandscape) {
                Image(
                    painter = painterResource(id = R.drawable.frame_1),
                    contentDescription = "ReadMe Logo"
                )
                Spacer(modifier = Modifier.height(50.dp))
            }

            ActionButton(text = "Start Lesson", onClick = { navController.navigate("lesson_screen") })
            Spacer(modifier = Modifier.height(8.dp))
            ActionButton(text = "Read Text", onClick = { navController.navigate("read_text_screen") })
        }
    }
}
