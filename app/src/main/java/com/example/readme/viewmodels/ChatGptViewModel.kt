package com.example.readme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.ChatRequest
import com.example.readme.data.Message
import com.example.readme.data.RetrofitClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ChatGptViewModel : ViewModel() {
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

}