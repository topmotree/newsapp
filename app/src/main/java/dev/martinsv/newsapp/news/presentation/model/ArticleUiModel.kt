package dev.martinsv.newsapp.news.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class ArticleUiModel(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAtFormatted: String?,
    val content: String?,
)