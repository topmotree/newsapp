package dev.martinsv.newsapp.news.presentation.model

import dev.martinsv.newsapp.news.domain.Article

sealed interface ArticleListUiState {
    object Loading : ArticleListUiState
    data class Success(val articles: List<ArticleUiModel>) : ArticleListUiState
    data class Error(val message: String) : ArticleListUiState
}