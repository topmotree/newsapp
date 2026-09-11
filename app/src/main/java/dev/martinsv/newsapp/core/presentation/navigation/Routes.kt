package dev.martinsv.newsapp.core.presentation.navigation

import dev.martinsv.newsapp.news.presentation.detail.ArticleDetailArgument
import kotlinx.serialization.Serializable

@Serializable
data object ArticleListRoute

@Serializable
data class ArticleDetailRoute(val argument: ArticleDetailArgument)