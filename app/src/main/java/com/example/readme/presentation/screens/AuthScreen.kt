package com.example.readme.presentation.screens

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.readme.presentation.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    val isLoggedIn = viewModel.isLoggedIn
    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLogin by remember { mutableStateOf(true) }
    var message by remember { mutableStateOf("") }

    // Interests and Level
    val interestsList = listOf("Reading", "Music", "Movies", "Sports", "Travel")
    var selectedInterests by remember { mutableStateOf(emptyList<String>()) }
    var selectedLevel by remember { mutableStateOf("Beginner") }

    // Navigate to MainScreen when logged in
    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate("main_screen") {
                popUpTo("auth_screen") { inclusive = true } // Clear back stack
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Interests and Level (Only in Sign Up)
        if (!isLogin) {
            // Interests
            Text("Select your Interests:")
            interestsList.forEach { interest ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = selectedInterests.contains(interest),
                        onCheckedChange = { isChecked ->
                            selectedInterests = if (isChecked) {
                                selectedInterests + interest
                            } else {
                                selectedInterests - interest
                            }
                        }
                    )
                    Text(interest)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Level (Radio Buttons)
            Text("Select your Level:")
            listOf("Beginner", "Intermediate", "Advanced").forEach { level ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = selectedLevel == level,
                        onCheckedChange = {
                            if (it) {
                                selectedLevel = level
                            }
                        }
                    )
                    Text(level)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(onClick = {
            coroutineScope.launch {
                if (isLogin) {
                    if (viewModel.signIn(email, password)) {
                        message = "Logged in"
                    } else {
                        message = "Login failed"
                    }
                } else {
                    if (viewModel.signUp(email, password, selectedInterests, selectedLevel)) {
                        message = "Account created"
                    } else {
                        message = "Sign up failed"
                    }
                }
            }
        }) {
            Text(text = if (isLogin) "Login" else "Sign Up")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (isLogin) "Don't have an account? Sign Up" else "Already have an account? Login",
            modifier = Modifier.clickable { isLogin = !isLogin }
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = message)
    }
}
