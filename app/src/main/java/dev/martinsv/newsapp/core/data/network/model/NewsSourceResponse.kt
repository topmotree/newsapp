package dev.martinsv.newsapp.core.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsEverythingResponse(
    //TODO handle error response - test it without api key

    //If the request was successful or not. Options: ok, error.
    //In the case of error a code and message property will be populated.
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto>
)