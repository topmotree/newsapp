package dev.martinsv.newsapp.news.data

import dev.martinsv.newsapp.news.data.model.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    /**
     * Fetches top and breaking headlines for a country, category, and/or keywords.
     *
     * @param country The 2-letter ISO 3166-1 code of the country to get headlines for.
     * @param category The category to get headlines for. One of: business, entertainment,
     * general, health, science, sports, technology. The API does not allow combining this with
     * a `sources` param (not exposed by this method).
     * @param query Keywords or a phrase to search for within the headlines.
     * @param pageSize The number of results to return per page. 20 is the default, 100 is the
     * maximum.
     * @param page The page number to fetch. Used together with [pageSize] to page through
     * results when the total results found is greater than the page size.
     * @return The [NewsResponseDto] containing the matching articles.
     */
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String? = null,
        @Query("q") query: String? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("page") page: Int? = null
    ): NewsResponseDto
}