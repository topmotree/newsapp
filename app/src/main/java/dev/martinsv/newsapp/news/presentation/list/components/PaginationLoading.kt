package dev.martinsv.newsapp.news.presentation.list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.martinsv.newsapp.core.presentation.theme.NewsappTheme

@Composable
fun PaginationLoading(
    modifier: Modifier = Modifier,
    containedHeight: Dp = 56.dp,
    indicatorSize: Dp = 24.dp,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(containedHeight),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(indicatorSize)
        )
    }
}

@Preview
@Composable
private fun PaginationLoadingPreview() {
    NewsappTheme {
        PaginationLoading()
    }
}