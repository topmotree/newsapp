package dev.martinsv.newsapp.news.presentation.list

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.martinsv.newsapp.news.domain.Article
import dev.martinsv.newsapp.news.domain.DEFAULT_PAGE_SIZE
import dev.martinsv.newsapp.news.domain.NewsRepository
import dev.martinsv.newsapp.news.presentation.list.paging.NewsPagingSource
import dev.martinsv.newsapp.news.presentation.list.paging.NewsType
import dev.martinsv.newsapp.news.presentation.mapper.ArticleUiMapper
import dev.martinsv.newsapp.news.presentation.model.ArticleUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val articleUiMapper: ArticleUiMapper,
) : ViewModel() {

    val searchFieldState = TextFieldState()

    private val _isSearchBarVisible = MutableStateFlow(false)
    val isSearchBarVisible = _isSearchBarVisible.asStateFlow()

    @OptIn(FlowPreview::class)
    val newsType: StateFlow<NewsType> = snapshotFlow { searchFieldState.text.toString() }
        .debounce(400.milliseconds)
        .distinctUntilChanged()
        .map {
            if (it.isBlank()) NewsType.TopHeadlines
            else NewsType.Everything(it)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NewsType.TopHeadlines)

    @OptIn(ExperimentalCoroutinesApi::class)
    val articlePagingFlow: Flow<PagingData<ArticleUiModel>> = newsType.flatMapLatest {
        createDefaultPager(it).flow
    }
        .map { pagingData ->
            pagingData.map { articleUiMapper.toUiModel(it) }
        }
        .cachedIn(viewModelScope)

    private val eventChannel = Channel<ArticleListEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onArticleClick(article: ArticleUiModel) {
        viewModelScope.launch { eventChannel.send(ArticleListEvent.OpenArticle(article)) }
    }

    fun onSearchIconClick() {
        val isVisible = _isSearchBarVisible.value
        searchFieldState.clearText()
        _isSearchBarVisible.update { !isVisible }
    }

    private fun createDefaultPager(newsType: NewsType): Pager<Int, Article> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                initialLoadSize = DEFAULT_PAGE_SIZE * 3,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsRepository = newsRepository,
                    newsType = newsType,
                )
            }
        )
    }
}