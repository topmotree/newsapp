package dev.martinsv.newsapp.news.presentation.model

sealed interface ArticleListUiState {
    object Loading : ArticleListUiState
    data class Success(val articles: List<ArticleUiModel>) : ArticleListUiState
    data object Error : ArticleListUiState
}