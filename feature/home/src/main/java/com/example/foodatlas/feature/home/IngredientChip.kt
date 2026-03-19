package com.example.foodatlas.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun IngredientChip(
    text: String,
    modifier: Modifier = Modifier
) {
    // "Parchment tags clipped to a page"
    // Using tertiaryContainer / onTertiaryContainer as approximations for tertiary_fixed
    val chipBgColor = MaterialTheme.colorScheme.tertiaryContainer 
    val chipTextColor = MaterialTheme.colorScheme.onTertiaryContainer

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(chipBgColor)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = chipTextColor
        )
    }
}
