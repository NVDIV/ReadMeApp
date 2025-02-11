package com.example.readme.presentation.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.width(150.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF7F85ED),
            contentColor = Color.White
        )
    ) {
        Text(text = text)
    }
}
