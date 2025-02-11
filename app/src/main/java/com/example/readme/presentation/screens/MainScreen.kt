package com.example.readme.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.readme.R
import com.example.readme.presentation.components.ActionButton
import com.example.readme.presentation.components.DrawerContent
import com.example.readme.presentation.components.TextMessage
import com.example.readme.presentation.components.TopBar
import kotlinx.coroutines.launch

@Composable
fun MainScreen(modifier: Modifier) {
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(250.dp),
                drawerContainerColor = Color(0xFF7F85ED)
            ) {
                DrawerContent()
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    onOpenDrawer = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
            }
        ) { padding ->
            ScreenContent(modifier = Modifier.padding(padding))
        }
    }
}



@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    val isDarkTheme = isSystemInDarkTheme()
    val textColor = if (isDarkTheme) Color.White else Color.Black
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
            TextMessage(
                message = "Hello, user! Welcome to Readme",
                highlights = listOf("user"),
                textColor = textColor
            )

            Spacer(modifier = Modifier.height(50.dp))

            if (!isLandscape) {
                Image(
                    painter = painterResource(id = R.drawable.frame_1),
                    contentDescription = "ReadMe Logo"
                )
                Spacer(modifier = Modifier.height(50.dp))
            }

            ActionButton(text = "Start Lesson", onClick = { /* TODO */ })
            Spacer(modifier = Modifier.height(8.dp))
            ActionButton(text = "Read Text", onClick = { /* TODO */ })
        }
    }
}
