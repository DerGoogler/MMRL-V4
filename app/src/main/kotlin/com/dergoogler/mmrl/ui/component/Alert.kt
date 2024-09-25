package com.dergoogler.mmrl.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

fun getTextColor(backgroundColor: Color): Color {
    val luminance =
        0.299 * backgroundColor.red + 0.587 * backgroundColor.green + 0.114 * backgroundColor.blue
    return if (luminance > 0.5) Color.Black else Color.White
}

@Composable
fun Alert(
    backgroundColor: Color,
    textColor: Color,
    title: String?,
    message: String,
    modifier: Modifier = Modifier
) {
//    val transparentBackgroundColor = backgroundColor.copy(alpha = 0.45f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if (title != null) {
                Text(
                    text = title,
                    color = textColor,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = message,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}