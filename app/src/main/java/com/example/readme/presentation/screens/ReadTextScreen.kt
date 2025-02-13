package com.example.readme.presentation.screens

import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.readme.presentation.components.DrawerLayout
import com.example.readme.presentation.viewmodels.AuthViewModel
import com.example.readme.viewmodels.ChatGptViewModel
import kotlinx.coroutines.launch

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
    var selectedWord by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Fetch user profile once
    LaunchedEffect(Unit) {
        authViewModel.getUserLevelAndInterests()?.let {
            level = it.first
            interests = it.second
        }
    }

    Column(modifier = modifier.padding(16.dp)) {

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { chatGptViewModel.generateText(interests, level) }) {
            Text("Generate")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Response:", style = TextStyle(fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold))
        Spacer(modifier = Modifier.height(8.dp))

        if (chatResponse.isNotEmpty()) {
            TextWithWordSelection(
                text = chatResponse,
                onWordSelected = { word -> selectedWord = word }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        selectedWord?.let { word ->
            Text("Selected word: $word", style = TextStyle(fontSize = 16.sp))
            Button(onClick = {
                coroutineScope.launch {
                    chatGptViewModel.addWordToDictionary(word)
                }
            }) {
                Text("Add to Dictionary")
            }
        }
    }
}

@Composable
fun TextWithWordSelection(
    text: String,
    onWordSelected: (String) -> Unit
) {
    var layoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    val annotatedString = buildAnnotatedString {
        text.split(" ").forEach { word ->
            pushStringAnnotation(tag = "WORD", annotation = word)
            append("$word ")
            pop()
        }
    }

    Text(
        text = annotatedString,
        style = TextStyle(fontSize = 18.sp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        layoutResult?.let { layout ->
                            val position = layout.getOffsetForPosition(offset)
                            annotatedString.getStringAnnotations(
                                tag = "WORD",
                                start = position,
                                end = position
                            ).firstOrNull()?.let { annotation ->
                                onWordSelected(annotation.item)
                            }
                        }
                    }
                )
            },
        onTextLayout = { textLayoutResult ->
            layoutResult = textLayoutResult
        }
    )
}


