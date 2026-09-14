package dev.martinsv.newsapp.news.data.repository

import dev.martinsv.newsapp.core.utils.DispatcherProvider
import dev.martinsv.newsapp.core.utils.logger.AppLogger
import dev.martinsv.newsapp.news.data.NewsApiService
import dev.martinsv.newsapp.news.data.mapper.NewsPageMapper
import dev.martinsv.newsapp.news.domain.NewsPage
import dev.martinsv.newsapp.news.domain.NewsRepository
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitNewsRepository @Inject constructor(
    private val newsApiService: NewsApiService,
    private val newsPageMapper: NewsPageMapper,
    private val dispatcher: DispatcherProvider,
    private val logger: AppLogger,
) : NewsRepository {

    override suspend fun getEverything(
        query: String,
        page: Int,
        pageSize: Int,
        language: String
    ): Result<NewsPage> = withContext(dispatcher.io) {
        try {
            val response = newsApiService.getEverything(
                query = query,
                page = page,
                pageSize = pageSize,
                language = language
            )
            Result.success(newsPageMapper.toDomain(response))
        } catch (e: Exception) {
            ensureActive()
            logger.e(tag = "RetrofitNewsRepository", error = e) { "Everything error" }
            Result.failure(e)
        }
    }

    override suspend fun getTopHeadlines(
        page: Int,
        pageSize: Int,
        country: String,
    ): Result<NewsPage> = withContext(dispatcher.io) {
        try {
            val response = newsApiService.getTopHeadlines(
                country = country,
                page = page,
                pageSize = pageSize,
            )

            Result.success(newsPageMapper.toDomain(response))
        } catch (e: Exception) {
            ensureActive()
            logger.e(tag = "RetrofitNewsRepository", error = e) { "Top headlines error" }
            Result.failure(e)
        }
    }
}
