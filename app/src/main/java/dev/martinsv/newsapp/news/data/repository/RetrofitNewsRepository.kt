package dev.martinsv.newsapp.news.data.repository

import dev.martinsv.newsapp.news.data.NewsApiService
import dev.martinsv.newsapp.news.data.mapper.NewsPageMapper
import dev.martinsv.newsapp.news.domain.NewsCountry
import dev.martinsv.newsapp.news.domain.NewsPage
import dev.martinsv.newsapp.news.domain.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitNewsRepository @Inject constructor(
    private val newsApiService: NewsApiService,
    private val newsPageMapper: NewsPageMapper,
) : NewsRepository {

    //TODO move dispatcher to the constructor
    override suspend fun getTopHeadlines(
        country: NewsCountry,
        page: Int,
        pageSize: Int,
    ): Result<NewsPage> = withContext(Dispatchers.IO) {
        runCatching {
            val newsResponse = newsApiService.getTopHeadlines(
                country = country.countryCode,
                page = page,
                pageSize = pageSize,
            )

            newsPageMapper.toDomain(newsResponse)
        }
    }
}
