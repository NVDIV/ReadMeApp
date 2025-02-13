package com.example.readme.data

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// OpenAI API URL
private const val BASE_URL = "https://api.openai.com/v1/"

// Retrofit instance
object RetrofitClient {
    val instance: ChatGptApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatGptApiService::class.java)
    }
}

// API Request and Response Data Models
data class ChatRequest(val model: String, val messages: List<Message>)
data class Message(val role: String, val content: String)
data class ChatResponse(val choices: List<Choice>)
data class Choice(val message: Message)

interface ChatGptApiService {
    @Headers("Authorization: Bearer ")
    @POST("chat/completions")
    suspend fun getChatResponse(@Body request: ChatRequest): ChatResponse
}
