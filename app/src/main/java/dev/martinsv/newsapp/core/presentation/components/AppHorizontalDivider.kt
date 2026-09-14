package dev.martinsv.newsapp.core.presentation.components

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AppHorizontalDivider(
    modifier: Modifier = Modifier,
) {
    HorizontalDivider(
        color = Color.LightGray,
        modifier = modifier,
    )
}