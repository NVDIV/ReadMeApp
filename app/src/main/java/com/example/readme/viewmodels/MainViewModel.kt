package com.example.readme.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _userName = MutableStateFlow("user")
    val userName = _userName.asStateFlow()

    fun updateUserName(name: String) {
        _userName.value = name
    }
}