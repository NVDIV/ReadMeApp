package com.example.readme.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.readme.presentation.components.DrawerLayout
import com.example.readme.viewmodels.ChatGptViewModel

@Composable
fun LessonScreen(navController: NavHostController, ) {
    DrawerLayout(navController) { modifier ->
          LessonScreenContent(modifier, viewModel())
    }
}

@Composable
fun LessonScreenContent(modifier: Modifier, viewModel: ChatGptViewModel) {
    val words by viewModel.dictionaryWords.collectAsState() // Fetch all words from Firestore
    var currentIndex by remember { mutableStateOf(0) }
    var showTranslation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchDictionaryWords()
    }

    val currentWord = words.getOrNull(currentIndex)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (currentWord == null) {
            Text("No words available. Add words to the dictionary!", textAlign = TextAlign.Center)
        } else {
            Text(
                text = currentWord.first, // Display the word
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (showTranslation) {
                Text(
                    text = currentWord.second, // Show translation if revealed
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { showTranslation = !showTranslation }) {
                    Text(if (showTranslation) "Hide Translation" else "Show Translation")
                }

                Button(onClick = {
                    if (currentIndex > 0) currentIndex--
                }, enabled = currentIndex > 0) {
                    Text("Previous")
                }

                Button(onClick = {
                    if (currentIndex < words.size - 1) currentIndex++
                }, enabled = currentIndex < words.size - 1) {
                    Text("Next")
                }
            }
        }
    }
}

