package dev.martinsv.newsapp.news.data.repository

import dev.martinsv.newsapp.news.domain.Article
import dev.martinsv.newsapp.news.domain.NewsPage
import dev.martinsv.newsapp.news.domain.NewsRepository
import kotlin.time.Clock

class FakeNewsRepository() : NewsRepository {
    private val articles: List<Article> = (1..100).map { createTestArticle(it) }

    override suspend fun getEverything(
        query: String,
        page: Int,
        pageSize: Int,
        language: String
    ): Result<NewsPage> =
        getPage(page, pageSize)

    override suspend fun getTopHeadlines(
        page: Int,
        pageSize: Int,
        country: String
    ): Result<NewsPage> =
        getPage(page, pageSize)

    private fun getPage(page: Int, pageSize: Int): Result<NewsPage> {
        val items = articles.drop((page - 1) * pageSize).take(pageSize)
        return Result.success(NewsPage(items, totalResults = articles.size))
    }

    private fun createTestArticle(id: Int) = Article(
        sourceName = "Source $id",
        author = "Author $id",
        title = "Title $id",
        description = "Description $id",
        url = "https://example.com/$id",
        urlToImage = "https://example.com/image-$id.jpg",
        publishedAt = Clock.System.now(),
        content = "Content $id",
    )
}