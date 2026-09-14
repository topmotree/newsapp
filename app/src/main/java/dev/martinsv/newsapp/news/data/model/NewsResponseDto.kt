package dev.martinsv.newsapp.news.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponseDto(
    //If the request was successful or not. Options: ok, error.
    //In the case of error a code and message property will be populated.
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto> = emptyList()
)