package dev.martinsv.newsapp.news.presentation.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import dev.martinsv.newsapp.core.presentation.icons.IconSearch
import dev.martinsv.newsapp.core.presentation.icons.IconSearchOff
import dev.martinsv.newsapp.core.presentation.utils.ObserveAsEvents
import dev.martinsv.newsapp.core.presentation.utils.hs
import dev.martinsv.newsapp.news.presentation.list.components.ArticleListItem
import dev.martinsv.newsapp.news.presentation.list.paging.NewsType
import dev.martinsv.newsapp.news.presentation.model.ArticleUiModel
import kotlinx.coroutines.flow.Flow


@Composable
fun ArticleListScreen(
    viewModel: ArticleListViewModel = hiltViewModel(),
    openArticleDetail: (ArticleUiModel) -> Unit
) {
    val articlesListState = rememberLazyListState()

    val articlePagingFlow = viewModel.articlePagingFlow
    val newsType by viewModel.newsType.collectAsStateWithLifecycle()
    val isSearchBarVisible by viewModel.isSearchBarVisible.collectAsStateWithLifecycle()

    LaunchedEffect(newsType) {
        articlesListState.scrollToItem(0)
    }

    ObserveAsEvents(viewModel.events) {
        when (it) {
            is ArticleListEvent.OpenArticle ->
                openArticleDetail(it.article)
        }
    }

    ArticleListContent(
        articlePagingFlow = articlePagingFlow,
        searchFieldState = viewModel.searchFieldState,
        onArticleClick = viewModel::onArticleClick,
        articlesListState = articlesListState,
        isSearchBarVisible = isSearchBarVisible,
        onSearchIconClick = viewModel::onSearchIconClick,
        newsType = newsType,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListContent(
    articlePagingFlow: Flow<PagingData<ArticleUiModel>>,
    searchFieldState: TextFieldState,
    onArticleClick: (ArticleUiModel) -> Unit,
    articlesListState: LazyListState,
    isSearchBarVisible: Boolean,
    onSearchIconClick: () -> Unit,
    newsType: NewsType,
    modifier: Modifier = Modifier,
) {
    val articlesPagingItems = articlePagingFlow.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            if (newsType == NewsType.TopHeadlines) {
                                hs("Top headlines")
                            } else {
                                hs("Search")
                            }

                        )
                    },
                    actions = {
                        IconButton(
                            onClick = onSearchIconClick
                        ) {
                            Icon(
                                imageVector = if (isSearchBarVisible) IconSearchOff else IconSearch,
                                contentDescription = null
                            )
                        }
                    }
                )

                AnimatedVisibility(isSearchBarVisible) {
                    OutlinedTextField(
                        state = searchFieldState,
                        placeholder = {
                            Text(hs("Search news"))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        },
        modifier = modifier,
    ) { scaffoldPadding ->

        //TODO handle paging loading and error states
        LazyColumn(
            modifier = Modifier.padding(scaffoldPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = articlesListState,
        ) {
            items(
                articlesPagingItems.itemCount,
                key = articlesPagingItems.itemKey { it.url ?: it.toString() }
            ) { index ->
                val article = articlesPagingItems[index]

                if (article != null) {
                    ArticleListItem(
                        article = article,
                        onClick = { onArticleClick(article) }
                    )
                } else {
                    //TODO redesign and add shimmer effect
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .background(Color.Gray)
                    )
                }
            }
        }
    }
}