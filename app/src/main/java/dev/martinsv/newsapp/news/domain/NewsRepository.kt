package dev.martinsv.newsapp.news.domain

//TODO is it good place for this const?
const val DEFAULT_PAGE_SIZE = 20

interface NewsRepository {
    suspend fun getTopHeadlines(
        country: NewsCountry = NewsCountry.USA,
        page: Int,
        pageSize: Int = DEFAULT_PAGE_SIZE,
    ): Result<NewsPage>
}