package com.example.readme.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    var isLoggedIn by mutableStateOf(false)
        private set

    var message by mutableStateOf("")
        private set

    var userEmail by mutableStateOf<String?>(null)
        private set

    init {
        isLoggedIn = auth.currentUser != null
        userEmail = auth.currentUser?.email
    }

    suspend fun signIn(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            isLoggedIn = true
            userEmail = auth.currentUser?.email
            true
        } catch (e: Exception) {
            message = e.message.toString()
            false
        }
    }

    suspend fun signUp(email: String, password: String, interests: List<String>, level: String): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()

            // Save user data in Firestore
            auth.currentUser?.let { user ->
                val userData = mapOf(
                    "email" to user.email,
                    "uid" to user.uid,
                    "interests" to interests,
                    "level" to level
                )
                firestore.collection("users")
                    .document(user.uid)
                    .set(userData)
                    .await()
            }

            isLoggedIn = true
            userEmail = email
            true
        } catch (e: Exception) {
            message = e.message.toString()
            false
        }
    }

    fun signOut() {
        auth.signOut()
        isLoggedIn = false
        userEmail = null
    }

    suspend fun getUserProfile(): Map<String, Any>? {
        val user = auth.currentUser ?: return null
        return try {
            val document = firestore.collection("users").document(user.uid).get().await()
            if (document.exists()) document.data else null
        } catch (e: Exception) {
            message = "Failed to fetch user profile: ${e.message}"
            null
        }
    }
}
