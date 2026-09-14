package dev.martinsv.newsapp.news.domain

const val DEFAULT_PAGE_SIZE = 20
const val DEFAULT_LANGUAGE = "en"

val DEFAULT_NEWS_COUNTRY = "us"

interface NewsRepository {

    suspend fun getEverything(
        query: String,
        page: Int,
        pageSize: Int,
        language: String = DEFAULT_LANGUAGE,
    ): Result<NewsPage>

    suspend fun getTopHeadlines(
        page: Int,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        country: String = DEFAULT_NEWS_COUNTRY,
    ): Result<NewsPage>
}