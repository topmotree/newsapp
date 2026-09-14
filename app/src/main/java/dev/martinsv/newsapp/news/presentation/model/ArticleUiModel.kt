package dev.martinsv.newsapp.news.presentation.model

import androidx.core.net.toUri
import kotlinx.serialization.Serializable

@Serializable
data class ArticleUiModel(
    val sourceName: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAtFormatted: String?,
    val content: String?,
) {
    val urlHost: String by lazy {
        url?.toUri()?.host ?: ""
    }
}