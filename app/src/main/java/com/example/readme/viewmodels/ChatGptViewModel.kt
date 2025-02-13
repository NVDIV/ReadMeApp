package com.example.readme.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.ChatRequest
import com.example.readme.data.Message
import com.example.readme.data.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class ChatGptViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _response = MutableStateFlow<String>("")
    val response: StateFlow<String> = _response

    fun generateText(interests: List<String>, level: String) {
        val interestString = interests.joinToString(", ")
        val prompt = """
        Generate a short text for an English learner. The text should:
        - Be suitable for a $level-level learner (beginner, intermediate, advanced).
        - Focus on the following interests: $interestString.
        Make it engaging and educational.
    """.trimIndent()

        viewModelScope.launch {
            try {
                val request = ChatRequest(
                    model = "gpt-3.5-turbo",
                    messages = listOf(Message(role = "user", content = prompt))
                )
                val result = RetrofitClient.instance.getChatResponse(request)
                _response.value = result.choices.firstOrNull()?.message?.content ?: "No response."
            } catch (e: Exception) {
                _response.value = "Error: ${e.message}"
            }
        }
    }

    suspend fun translateWord(word: String): String? {
        val prompt = "Translate the following word into polish:\n\n$word"

            return try {
                val request = ChatRequest(
                    model = "gpt-3.5-turbo",
                    messages = listOf(Message(role = "user", content = prompt))
                )
                val result = RetrofitClient.instance.getChatResponse(request)
                result.choices.firstOrNull()?.message?.content?.trim()
            } catch (e: Exception) {
                Log.e("ChatGptViewModel", "Translation error: ${e.message}")
                null
            }
        }


    suspend fun addWordToDictionary(word: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Log.e("ChatGptViewModel", "No user is logged in.")
            return
        }

        val userId = currentUser.uid
        val dictionaryRef = firestore.collection("users").document(userId).collection("dictionary")

        // Clean the word by removing unnecessary characters
        val cleanedWord = word.replace(Regex("[^a-zA-Z]"), "").lowercase()

        if (cleanedWord.isEmpty()) {
            Log.e("ChatGptViewModel", "Invalid word: $word")
            return
        }

        try {
            // Translate the word using TranslationClient

            val translatedWord = translateWord(cleanedWord) // This function should return the translated word as a String.

            if (translatedWord.isNullOrEmpty()) {
                Log.e("ChatGptViewModel", "Translation failed for word: $cleanedWord")
                return
            }

            // Prepare the word entry for Firestore
            val wordEntry = hashMapOf(
                "originalWord" to cleanedWord,
                "translatedWord" to translatedWord,
                "timestamp" to System.currentTimeMillis()
            )

            // Add to Firestore
            dictionaryRef.add(wordEntry).await()
            Log.d("ChatGptViewModel", "Word added to dictionary: $cleanedWord -> $translatedWord")

        } catch (e: Exception) {
            Log.e("ChatGptViewModel", "Failed to add word to dictionary: ${e.message}")
        }
    }
}