package dev.martinsv.newsapp.news.presentation.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.martinsv.newsapp.core.presentation.icons.IconError
import dev.martinsv.newsapp.core.presentation.theme.NewsappTheme
import dev.martinsv.newsapp.core.presentation.utils.hs

@Composable
fun RefreshError(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Icon(
            imageVector = IconError,
            tint = MaterialTheme.colorScheme.error,
            contentDescription = hs("Error icon"),
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = hs("Error occurred"),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = hs("Check your connection or try again."),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRetry) {
            Text(hs("Retry"))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RefreshErrorDisplayPreview() {
    NewsappTheme {
        RefreshError(
            onRetry = { },
            modifier = Modifier.fillMaxSize(),
        )
    }
}