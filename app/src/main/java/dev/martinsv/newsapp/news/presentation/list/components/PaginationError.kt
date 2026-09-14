package dev.martinsv.newsapp.news.presentation.list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.martinsv.newsapp.R
import dev.martinsv.newsapp.core.presentation.theme.NewsappTheme

@Composable
fun PaginationError(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        onClick = onRetry,
        color = MaterialTheme.colorScheme.errorContainer,
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 16.dp, end = 8.dp)
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.article_list_pagination_error_message),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )

            TextButton(
                onClick = onRetry,
                colors = ButtonDefaults.textButtonColors().copy(
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                )
            ) {
                Text(
                    text = stringResource(R.string.global_retry_button_label),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                )
            }

        }
    }
}

@Preview
@Composable
private fun PaginationErrorPreview() {
    Column {
        NewsappTheme(darkTheme = true) {
            PaginationError(
                onRetry = {},
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        NewsappTheme(darkTheme = false) {
            PaginationError(
                onRetry = {},
            )
        }
    }

}