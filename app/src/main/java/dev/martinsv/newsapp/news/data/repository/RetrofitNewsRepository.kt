package dev.martinsv.newsapp.news.data.repository

import android.util.Log
import dev.martinsv.newsapp.core.utils.DispatcherProvider
import dev.martinsv.newsapp.news.data.NewsApiService
import dev.martinsv.newsapp.news.data.mapper.NewsPageMapper
import dev.martinsv.newsapp.news.domain.NewsCountry
import dev.martinsv.newsapp.news.domain.NewsPage
import dev.martinsv.newsapp.news.domain.NewsRepository
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitNewsRepository @Inject constructor(
    private val newsApiService: NewsApiService,
    private val newsPageMapper: NewsPageMapper,
    private val dispatcher: DispatcherProvider,
) : NewsRepository {

    override suspend fun getTopHeadlines(
        page: Int,
        pageSize: Int,
        country: NewsCountry,
    ): Result<NewsPage> = withContext(dispatcher.io) {
        try {
            val newsResponse = newsApiService.getTopHeadlines(
                country = country.countryCode,
                page = page,
                pageSize = pageSize,
            )

            Result.success(newsPageMapper.toDomain(newsResponse))
        } catch (e: Exception) {
            currentCoroutineContext().ensureActive()
            //TODO add app logger
            Log.e("RetrofitNewsRepository", "Top headlines error", e)
            Result.failure(e)
        }
    }
}
