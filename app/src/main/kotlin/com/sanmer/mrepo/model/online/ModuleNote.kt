package com.sanmer.mrepo.model.online

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModuleNote(
    val color: String = "",
    val title: String = "",
    val message: String = "",
) {
    fun isNotEmpty() = color.isNotEmpty() && title.isNotEmpty() && message.isNotEmpty()

    val textColor= {
        when (color) {
            "red" -> Color(0xFFB71C1C)
            "blue" -> Color(0xFF0D47A1)
            "yellow" -> Color(0xFF5F5F00)
            "green" -> Color(0xFF1B5E20)
            "purple" -> Color(0xFF4A148C)
            else -> Color(0xFF0D47A1)
        }
    }

    val backgroundColor = {
        when (color) {
            "red" -> Color(0xFFFFCDD2)
            "blue" -> Color(0xFF90CAF9)
            "yellow" -> Color(0xFFFFF59D)
            "green" -> Color(0xFF81C784)
            "purple" -> Color(0xFFCE93D8)
            else -> Color(0xFF90CAF9)
        }
    }
}

