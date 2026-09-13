package dev.martinsv.newsapp.news.data

import dev.martinsv.newsapp.news.data.model.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    //TODO add kdoc
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
     * @param page The page number to fetch. Used together with [pageSize] to page through
     * results when the total results found is greater than the page size.
     * @return The [NewsResponseDto] containing the matching articles.
     */
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("page") page: Int? = null
    ): NewsResponseDto
}