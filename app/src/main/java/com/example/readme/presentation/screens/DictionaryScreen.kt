package com.example.readme.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.readme.presentation.components.DrawerLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun DictionaryScreen(
    navController: NavHostController,
) {
    DrawerLayout(navController) { modifier ->
          DictionaryScreenContent(modifier)
    }
}

@Composable
fun DictionaryScreenContent(modifier: Modifier) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val firestore = FirebaseFirestore.getInstance()
    val words = remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        if (currentUser != null) {
            try {
                val snapshot = firestore.collection("users")
                    .document(currentUser.uid)
                    .collection("dictionary")
                    .get()
                    .await()
                words.value = snapshot.documents.mapNotNull { it.data }
            } catch (e: Exception) {
                errorMessage.value = "Failed to load words: ${e.message}"
            }
        } else {
            errorMessage.value = "No user is logged in."
        }
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "My Dictionary",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (errorMessage.value != null) {
            Text(text = errorMessage.value ?: "An error occurred.", color = MaterialTheme.colorScheme.error)
        } else if (words.value.isEmpty()) {
            Text(text = "No words found in your dictionary.")
        } else {
            words.value.forEach { wordEntry ->
                val originalWord = wordEntry["originalWord"] as? String ?: "N/A"
                val translatedWord = wordEntry["translatedWord"] as? String ?: "N/A"
                WordItem(originalWord, translatedWord)
            }
        }
    }
}

@Composable
fun WordItem(originalWord: String, translatedWord: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = "Word: $originalWord", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Translation: $translatedWord", style = MaterialTheme.typography.bodySmall)
    }
}