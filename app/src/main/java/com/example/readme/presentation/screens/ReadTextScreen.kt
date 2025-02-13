package com.example.readme.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.readme.presentation.components.DrawerLayout
import com.example.readme.presentation.viewmodels.AuthViewModel
import com.example.readme.viewmodels.ChatGptViewModel

@Composable
fun ReadTextScreen(navController: NavHostController, ) {
    DrawerLayout(navController) { modifier ->
        ReadTextScreenContent(modifier) // Pass padding modifier
    }
}

@Composable
fun ReadTextScreenContent(modifier: Modifier) {
    val chatGptViewModel: ChatGptViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()

    var interests by remember { mutableStateOf<List<String>>(emptyList()) }
    var level by remember { mutableStateOf("") }
    val chatResponse by chatGptViewModel.response.collectAsState()

    // Fetch user profile once
    LaunchedEffect(Unit) {
        authViewModel.getUserLevelAndInterests()?.let {
            level = it.first
            interests = it.second
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Generate a Text Based on Your Profile", modifier = Modifier.padding(bottom = 8.dp))

        Button(onClick = { chatGptViewModel.generateText(interests, level) }) {
            Text("Generate")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Response:", modifier = Modifier.padding(top = 8.dp))
        Text(chatResponse)
    }
}
