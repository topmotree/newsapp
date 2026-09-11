package dev.martinsv.newsapp.news.domain

data class NewsPage(
    val articles: List<Article>,
    val totalResults: Int,
)