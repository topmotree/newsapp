package dev.martinsv.newsapp.news.data.mapper

import dev.martinsv.newsapp.news.data.model.NewsResponseDto
import dev.martinsv.newsapp.news.domain.NewsPage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsPageMapper @Inject constructor(
    private val articleMapper: ArticleMapper,
) {

    fun toDomain(dto: NewsResponseDto): NewsPage =
        NewsPage(
            articles = dto.articles.map { articleMapper.toDomain(it) },
            totalResults = dto.totalResults,
        )
}