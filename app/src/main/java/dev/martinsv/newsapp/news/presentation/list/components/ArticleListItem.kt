package dev.martinsv.newsapp.news.presentation.list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.martinsv.newsapp.R
import dev.martinsv.newsapp.core.presentation.theme.NewsappTheme
import dev.martinsv.newsapp.core.utils.logger.logger
import dev.martinsv.newsapp.news.presentation.model.ArticleUiModel
import dev.martinsv.newsapp.news.presentation.utils.PreviewData

@Composable
fun ArticleListItem(
    article: ArticleUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageShape: Shape = RoundedCornerShape(16.dp),
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            AsyncImage(
                model = article.urlToImage,
                placeholder = painterResource(R.drawable.image_placeholder),
                error = painterResource(R.drawable.image_placeholder),
                contentDescription = stringResource(R.string.article_image_content_description),
                contentScale = ContentScale.Crop,
                onError = {
                    logger.d { "Image loading Error. Result: ${it.result}" }
                },
                modifier = Modifier
                    .aspectRatio(112.0f / 84)
                    .weight(1f)
                    .clip(imageShape),
            )

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(2.5f)
            ) {
                article.sourceName?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp)
                    )
                }

                article.title?.let { title ->
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                article.description?.let { description ->
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ArticleListItemPreview() {
    NewsappTheme {
        Column {
            ArticleListItem(
                article = PreviewData.article1,
                onClick = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            ArticleListItem(
                article = PreviewData.article2,
                onClick = {}
            )
        }
    }
}