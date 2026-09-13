package dev.martinsv.newsapp.news.domain

const val DEFAULT_PAGE_SIZE = 20
const val DEFAULT_LANGUAGE = "en"

//TODO Next: remove NewsCountry completely
val DEFAULT_NEWS_COUNTRY = NewsCountry.USA

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
        country: NewsCountry = DEFAULT_NEWS_COUNTRY,
    ): Result<NewsPage>
}