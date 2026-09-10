package dev.martinsv.newsapp.core.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SourceDto(
    val id: String? = null,
    val name: String? = null,
)