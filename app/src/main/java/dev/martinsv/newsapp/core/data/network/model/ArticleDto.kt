package dev.martinsv.newsapp.core.data.network.model

import kotlinx.serialization.Serializable

//TODO consider change fields to nullable - to prevent serialization exception. SourceDto as well
@Serializable
data class ArticleDto(
    val source: SourceDto,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)
