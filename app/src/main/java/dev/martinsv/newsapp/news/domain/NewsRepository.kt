package dev.martinsv.newsapp.news.domain

const val DEFAULT_PAGE_SIZE = 20

interface NewsRepository {
    suspend fun getTopHeadlines(
        page: Int,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        country: NewsCountry = NewsCountry.USA,
    ): Result<NewsPage>
}