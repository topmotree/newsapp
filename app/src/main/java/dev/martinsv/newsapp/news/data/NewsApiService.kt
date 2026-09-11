package dev.martinsv.newsapp.news.data

import dev.martinsv.newsapp.news.data.model.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("language") language: String? = null,
        @Query("sortBy") sortBy: String? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("page") page: Int? = null
    ): NewsResponseDto

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String? = null,
        @Query("language") language: String? = null,
        @Query("q") query: String? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("page") page: Int? = null
    ): NewsResponseDto
}