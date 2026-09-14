package dev.martinsv.newsapp.news.data

import dev.martinsv.newsapp.news.data.model.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    /**
     * Search through millions of articles from over 150,000 large and small news sources and blogs.
     *
     * @param query Keywords or phrases to search for in the article title and body.
     * @param language The 2-letter ISO-639-1 code of the language you want to get headlines for.
     * @param pageSize The number of results to return per page.
     * @param page The page number to fetch
     *
     * */
    @GET("v2/everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("language") language: String? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("page") page: Int? = null
    ): NewsResponseDto

    /**
     * Fetches top and breaking headlines
     *
     * @param country The 2-letter ISO 3166-1 code of the country to get headlines for.
     * @param pageSize The number of results to return per page. 20 is the default, 100 is the
     * maximum.
     * @param page The page number to fetch.
     */
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("page") page: Int? = null
    ): NewsResponseDto
}