package dev.martinsv.newsapp.news.data.mapper

import dev.martinsv.newsapp.news.data.model.ArticleDto
import dev.martinsv.newsapp.news.domain.Article
import kotlin.time.Instant

class ArticleMapper {

    fun toDomain(dto: ArticleDto): Article =
        Article(
            sourceName = dto.source?.name,
            author = dto.author,
            title = dto.title,
            description = dto.description,
            url = dto.url,
            urlToImage = dto.urlToImage,
            publishedAt = dto.publishedAt?.let { Instant.parse(it) },
            content = dto.content
        )
}