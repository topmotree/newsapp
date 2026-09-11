package dev.martinsv.newsapp.news.presentation.detail

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.martinsv.newsapp.core.presentation.utils.ObserveAsEvents
import dev.martinsv.newsapp.core.presentation.utils.hs
import dev.martinsv.newsapp.core.presentation.utils.openCustomTab
import dev.martinsv.newsapp.news.presentation.model.ArticleUiModel

@Composable
fun ArticleDetailScreen(
    viewModel: ArticleDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ArticleDetailEvent.OpenUrl ->
                context.openCustomTab(event.url)
        }
    }

    ArticleDetailContent(
        article = viewModel.article,
        onReadFullArticleClick = viewModel::onReadFullArticleClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailContent(
    article: ArticleUiModel,
    onReadFullArticleClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            //TODO add back navigation
            TopAppBar(
                title = {
                    Text(hs("Article"))
                }
            )
        },
        modifier = modifier,
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues
        ) {


            article.title?.let {
                item("title") {
                    Text(it)
                }
            }

            article.content?.let {
                item("content") {
                    Text(it)
                }
            }

            article.url?.let {
                item {
                    Button(
                        onClick = {
                            onReadFullArticleClick()
                        }
                    ) {
                        Text(hs("Read full"))
                    }
                }
            }
        }
    }
}