package dev.martinsv.newsapp.news.presentation.list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.martinsv.newsapp.core.presentation.icons.IconCheckCircle
import dev.martinsv.newsapp.core.presentation.theme.NewsappTheme
import dev.martinsv.newsapp.core.presentation.utils.hs

@Composable
fun PaginationEndReached(
    modifier: Modifier = Modifier,
    containerHeight: Dp = 56.dp,
    iconSize: Dp = 24.dp,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(containerHeight),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = IconCheckCircle,
            contentDescription = hs("No more articles"),
            modifier = Modifier.size(iconSize),
            tint = MaterialTheme.colorScheme.outline,
        )
    }
}

@Preview
@Composable
private fun PaginationEndReachedPreview() {
    NewsappTheme {
        PaginationEndReached()
    }
}