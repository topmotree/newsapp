package dev.martinsv.newsapp.news.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.martinsv.newsapp.core.presentation.icons.IconError
import dev.martinsv.newsapp.core.presentation.utils.ObserveAsEvents
import dev.martinsv.newsapp.core.presentation.utils.hs
import dev.martinsv.newsapp.news.presentation.list.components.ArticleListItem
import dev.martinsv.newsapp.news.presentation.model.ArticleListUiState
import dev.martinsv.newsapp.news.presentation.model.ArticleUiModel

@Composable
fun ArticleListScreen(
    viewModel: ArticleListViewModel = hiltViewModel(),
    openArticleDetail: (ArticleUiModel) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) {
        when (it) {
            is ArticleListEvent.OpenArticle ->
                openArticleDetail(it.article)
        }
    }

    ArticleListContent(
        state = state,
        onArticleClick = viewModel::onArticleClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListContent(
    state: ArticleListUiState,
    onArticleClick: (ArticleUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(hs("News"))
            })
        },
        modifier = modifier,
    ) { scaffoldPadding ->

        when (state) {
            ArticleListUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(scaffoldPadding)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            is ArticleListUiState.Error ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(scaffoldPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = IconError,
                        tint = MaterialTheme.colorScheme.error,
                        contentDescription = hs("Error icon"),
                        modifier = Modifier.size(32.dp)
                    )

                    Text(
                        text = state.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }

            is ArticleListUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(scaffoldPadding),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(
                        state.articles,
                        //TODO what will be on hashcode collisions?
                        key = { it.url ?: it.hashCode().toString() }
                    ) { article ->
                        ArticleListItem(
                            article = article,
                            onClick = { onArticleClick(article) }
                        )
                    }
                }
            }
        }
    }
}