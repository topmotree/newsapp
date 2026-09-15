package dev.martinsv.newsapp.news.data.repository

import dev.martinsv.newsapp.news.domain.Article
import dev.martinsv.newsapp.news.domain.NewsPage
import dev.martinsv.newsapp.news.domain.NewsRepository

class FakeNewsRepository(
    articlesCount: Int = 100,
) : NewsRepository {
    private val articles: List<Article> = (1..articlesCount).map { createTestArticle(it) }

    @Volatile
    var error: Throwable? = null

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
        error?.let { return Result.failure(it) }
        val items = articles.drop((page - 1) * pageSize).take(pageSize)
        return Result.success(NewsPage(items, totalResults = articles.size))
    }
}

internal fun createTestArticle(id: Int) = Article(
    sourceName = "Source $id",
    author = "Author $id",
    title = "Title $id",
    description = "Description $id",
    url = "https://example.com/$id",
    urlToImage = null,
    publishedAt = null,
    content = "Content $id",
)