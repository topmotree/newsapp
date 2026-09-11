package dev.martinsv.newsapp.news.presentation.list

import dev.martinsv.newsapp.news.presentation.model.ArticleUiModel

sealed interface ArticleListEvent {
    data class OpenArticle(val article: ArticleUiModel) : ArticleListEvent
}