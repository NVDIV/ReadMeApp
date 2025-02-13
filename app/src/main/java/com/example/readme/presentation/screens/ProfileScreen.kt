package com.example.readme.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.readme.presentation.components.DrawerLayout
import com.example.readme.presentation.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    var userData by remember { mutableStateOf<Map<String, Any>?>(null) }
    var isLoading by remember { mutableStateOf(true) } // Track loading state

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            userData = viewModel.getUserProfile()
            isLoading = false // Stop loading once data is fetched
        }
    }

    DrawerLayout(navController) { modifier ->
        ProfileScreenContent(
            modifier,
            userData,
            isLoading, // Pass loading state
            onSignOut = {
                viewModel.signOut()
                navController.navigate("auth_screen") {
                    popUpTo("profile_screen") { inclusive = true }
                }
            }
        )
    }
}

@Composable
fun ProfileScreenContent(
    modifier: Modifier,
    userData: Map<String, Any>?,
    isLoading: Boolean,
    onSignOut: () -> Unit
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Profile", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))

        if (isLoading) {
            Text("Loading...")
        } else {
            userData?.let {
                Text("Email: ${it["email"] ?: "Unknown"}")
                Text("Level: ${it["level"] ?: "Unknown"}")
                Text("Interests:")
                (it["interests"] as? List<*>)?.forEach { interest ->
                    Text("- $interest")
                }
            } ?: Text("Failed to load profile data.")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onSignOut) {
            Text("Sign Out")
        }
    }
}

