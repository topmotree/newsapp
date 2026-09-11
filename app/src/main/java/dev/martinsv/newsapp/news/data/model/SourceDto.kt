package dev.martinsv.newsapp.news.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SourceDto(
    val id: String? = null,
    val name: String? = null,
)