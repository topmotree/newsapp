package dev.martinsv.newsapp.news.presentation.detail

sealed interface ArticleDetailEvent {
    data class OpenUrl(val url: String) : ArticleDetailEvent
}