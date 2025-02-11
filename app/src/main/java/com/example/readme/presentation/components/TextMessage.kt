package com.example.readme.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun TextMessage(message: String, highlights: List<String>, textColor: Color) {
    val annotatedString = buildAnnotatedString {
        var lastIndex = 0
        highlights.forEach { highlight ->
            val startIndex = message.indexOf(highlight, lastIndex)
            if (startIndex != -1) {
                append(message.substring(lastIndex, startIndex))
                withStyle(style = SpanStyle(color = Color(0xFF676CC3))) {
                    append(highlight)
                }
                lastIndex = startIndex + highlight.length
            }
        }
        append(message.substring(lastIndex))
    }

    Text(
        text = annotatedString,
        fontSize = 18.sp,
        color = textColor
    )
}
