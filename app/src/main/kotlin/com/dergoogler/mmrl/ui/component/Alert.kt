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

@Composable
fun Alert(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    textColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    title: String?,
    message: String,
) {
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
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (title != null) {
                Text(
                    text = title,
                    color = textColor,
                    style = MaterialTheme.typography.titleMedium,
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