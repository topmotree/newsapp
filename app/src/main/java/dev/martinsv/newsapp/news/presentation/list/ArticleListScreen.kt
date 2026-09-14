package dev.martinsv.newsapp.news.presentation.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.PagingData.Companion.from
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import dev.martinsv.newsapp.core.presentation.theme.NewsappTheme
import dev.martinsv.newsapp.core.presentation.utils.ObserveAsEvents
import dev.martinsv.newsapp.news.presentation.list.components.ArticleListItem
import dev.martinsv.newsapp.news.presentation.list.components.ArticleListLoadingItem
import dev.martinsv.newsapp.news.presentation.list.components.ArticleListTopBar
import dev.martinsv.newsapp.news.presentation.list.components.NothingFoundHint
import dev.martinsv.newsapp.news.presentation.list.components.PaginationEndReached
import dev.martinsv.newsapp.news.presentation.list.components.PaginationError
import dev.martinsv.newsapp.news.presentation.list.components.PaginationLoading
import dev.martinsv.newsapp.news.presentation.list.components.RefreshError
import dev.martinsv.newsapp.news.presentation.list.paging.NewsType
import dev.martinsv.newsapp.news.presentation.model.ArticleUiModel
import dev.martinsv.newsapp.news.presentation.utils.PreviewData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


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
            ArticleListTopBar(
                newsType = newsType,
                onSearchIconClick = onSearchIconClick,
                isSearchBarVisible = isSearchBarVisible,
                searchFieldState = searchFieldState
            )
        },
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets()
    ) { scaffoldPadding ->

        Box {
            if (articlesPagingItems.loadState.refresh is LoadState.Error) {
                RefreshError(
                    onRetry = { articlesPagingItems.retry() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(scaffoldPadding)
                        .padding(16.dp),
                )
            } else if (searchFieldState.text.isNotBlank() && articlesPagingItems.itemCount == 0) {
                NothingFoundHint(
                    searchQuery = searchFieldState.text.toString(),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(scaffoldPadding)
                        .padding(16.dp),
                )
            } else {
                //TODO test keyboard insets on real device. Probably required imaPadding
                LazyColumn(
                    modifier = Modifier.padding(scaffoldPadding),
                    state = articlesListState,
                    contentPadding = WindowInsets.systemBars
                        .only(WindowInsetsSides.Bottom)
                        .asPaddingValues()
                ) {
                    if (articlesPagingItems.loadState.refresh is LoadState.Loading) {
                        items(6) {
                            ArticleListLoadingItem()

                            HorizontalDivider(color = Color.LightGray)
                        }
                    }

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
                            ArticleListLoadingItem()
                        }

                        HorizontalDivider(color = Color.LightGray)
                    }

                    item("pagination_footer") {
                        val appendState = articlesPagingItems.loadState.append

                        when {
                            appendState is LoadState.Loading ->
                                PaginationLoading(
                                    modifier = Modifier.padding(16.dp)
                                )

                            appendState is LoadState.Error ->
                                PaginationError(
                                    onRetry = { articlesPagingItems.retry() },
                                    modifier = Modifier.padding(16.dp)
                                )

                            appendState.endOfPaginationReached ->
                                PaginationEndReached(
                                    modifier = Modifier.padding(16.dp),
                                )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun ArticleListContentPreview() {
    val articlePagingFlow =
        MutableStateFlow(from(PreviewData.list))

    NewsappTheme {
        ArticleListContent(
            articlePagingFlow = articlePagingFlow,
            searchFieldState = rememberTextFieldState(),
            onArticleClick = {},
            articlesListState = rememberLazyListState(),
            isSearchBarVisible = false,
            onSearchIconClick = {},
            newsType = NewsType.TopHeadlines,
        )
    }
}