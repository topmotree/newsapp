package dev.martinsv.newsapp.news.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import dev.martinsv.newsapp.R
import dev.martinsv.newsapp.core.presentation.components.AppHorizontalDivider
import dev.martinsv.newsapp.core.presentation.icons.IconArrowBack
import dev.martinsv.newsapp.core.presentation.icons.IconOpenInNew
import dev.martinsv.newsapp.core.presentation.theme.NewsappTheme
import dev.martinsv.newsapp.core.presentation.utils.ObserveAsEvents
import dev.martinsv.newsapp.core.presentation.utils.hs
import dev.martinsv.newsapp.core.presentation.utils.openCustomTab
import dev.martinsv.newsapp.core.utils.logger.logger
import dev.martinsv.newsapp.news.presentation.model.ArticleUiModel
import dev.martinsv.newsapp.news.presentation.utils.PreviewData

@Composable
fun ArticleDetailScreen(
    viewModel: ArticleDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ArticleDetailEvent.OpenUrl ->
                context.openCustomTab(event.url)

            ArticleDetailEvent.OnBack ->
                onBackClick()
        }
    }

    ArticleDetailContent(
        article = viewModel.article,
        onReadFullArticleClick = viewModel::onReadFullArticleClick,
        onBackClick = viewModel::onBackNavigationClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailContent(
    article: ArticleUiModel,
    onReadFullArticleClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Icon(
                            imageVector = IconArrowBack,
                            contentDescription = hs("Go to previous screen")
                        )
                    }
                }
            )
        },
        modifier = modifier,
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues
        ) {
            if (article.urlToImage != null) {
                item("article_image") {
                    AsyncImage(
                        model = article.urlToImage,
                        placeholder = painterResource(R.drawable.image_placeholder),
                        error = painterResource(R.drawable.image_placeholder),
                        contentDescription = hs("Article image"),
                        contentScale = ContentScale.Crop,
                        onError = {
                            logger.d { "Image loading Error. Result: ${it.result}" }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 260.dp),
                    )
                }
            }

            article.title?.let {
                item("title") {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            if (article.sourceName != null || article.author != null) {
                item("source_and_author") {
                    val sourceAndAuthor = joinSourceAndAuthor(article)

                    Text(
                        text = sourceAndAuthor ?: "",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 6.dp)
                    )
                }
            }

            article.publishedAtFormatted?.let {
                item("published_data") {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )
                }
            }

            item("divider") {
                AppHorizontalDivider(
                    modifier = Modifier.padding(16.dp)
                )
            }

            article.description?.let {
                item("article_description") {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp)
                    )
                }
            }

            article.content?.let {
                item("article_content") {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp)
                    )
                }
            }

            article.url?.let {
                item("open_article_button") {
                    OpenArticleBlock(
                        article = article,
                        onReadFullArticleClick = onReadFullArticleClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun OpenArticleBlock(
    article: ArticleUiModel,
    onReadFullArticleClick: () -> Unit,
) {
    Column {
        Button(
            onClick = {
                onReadFullArticleClick()
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = hs("Read full article"),
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                imageVector = IconOpenInNew,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = hs("Opens ${article.urlHost} in your browser"),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
    }
}

private fun joinSourceAndAuthor(article: ArticleUiModel): String? =
    if (article.sourceName != null) listOfNotNull(
        article.sourceName,
        article.author
    ).joinToString(" · ") else article.author

@Preview(showBackground = true)
@Composable
private fun ArticleDetailContentPreview() {
    NewsappTheme {
        ArticleDetailContent(
            article = PreviewData.article2,
            onReadFullArticleClick = {},
            onBackClick = {},
        )
    }
}