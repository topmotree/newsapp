package dev.martinsv.newsapp.news.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.martinsv.newsapp.core.utils.DispatcherProvider
import dev.martinsv.newsapp.news.domain.Article
import dev.martinsv.newsapp.news.domain.NewsRepository
import dev.martinsv.newsapp.news.presentation.mapper.ArticleUiMapper
import dev.martinsv.newsapp.news.presentation.model.ArticleListUiState
import dev.martinsv.newsapp.news.presentation.model.ArticleUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val articleUiMapper: ArticleUiMapper,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private var hasLoadedInitialData = false

    //TODO replace state on Paging3 flow and add pagination
    private val _state = MutableStateFlow<ArticleListUiState>(ArticleListUiState.Loading)
    val state: StateFlow<ArticleListUiState> = _state
        .onStart {
            if (!hasLoadedInitialData) {
                fetchArticles()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ArticleListUiState.Loading,
        )


    private fun fetchArticles() {
        viewModelScope.launch {
            _state.update { ArticleListUiState.Loading }

            newsRepository.getTopHeadlines(page = 1)
                .onSuccess { data ->
                    _state.update { ArticleListUiState.Success(mapToUiModel(data.articles)) }
                }
                .onFailure {
                    //TODO consider adding different error messages by exception type
                    _state.update { ArticleListUiState.Error("Error occurred") }
                }
        }
    }

    private suspend fun mapToUiModel(articles: List<Article>): List<ArticleUiModel> =
        withContext(dispatcherProvider.default) {
            articles.map { articleUiMapper.toUiModel(it) }
        }
}