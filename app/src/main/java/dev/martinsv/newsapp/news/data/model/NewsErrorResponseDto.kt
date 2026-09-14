package dev.martinsv.newsapp.news.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsErrorResponseDto(
    val status: String,
    val code: String? = null,
    val message: String? = null,
)