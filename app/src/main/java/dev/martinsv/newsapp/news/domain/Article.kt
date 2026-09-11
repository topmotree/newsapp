package dev.martinsv.newsapp.news.domain

import kotlin.time.Instant

data class Article(
    val sourceName: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: Instant?,
    val content: String?
)