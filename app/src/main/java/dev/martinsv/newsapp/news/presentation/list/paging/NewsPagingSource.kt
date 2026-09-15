package dev.martinsv.newsapp.news.presentation.list.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.martinsv.newsapp.news.domain.Article
import dev.martinsv.newsapp.news.domain.DEFAULT_PAGE_SIZE
import dev.martinsv.newsapp.news.domain.NewsRepository
import dev.martinsv.newsapp.news.presentation.list.paging.NewsType.Everything
import dev.martinsv.newsapp.news.presentation.list.paging.NewsType.TopHeadlines

sealed interface NewsType {
    data object TopHeadlines : NewsType
    data class Everything(val query: String) : NewsType
}

class NewsPagingSource(
    private val newsRepository: NewsRepository,
    private val newsType: NewsType,
    //TODO handle when pageSize is more than 100
    private val pageSize: Int = DEFAULT_PAGE_SIZE,
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val nextPageNumber = params.key ?: 1

        // By default Paging loads 3x pageSize on the first load (60 if pageSize is 20), then init pageSize.
        // NewsAPI counts pages by the requested size, so page 2 with size 20 would repeat items 21..40.
        // So page 1 loads several pages in one request, and the next key skips them (60 items -> page 4).

        // Setting initialLoadSize = pageSize in PagingConfig also works, but needs more requests.
        val pagesToLoad =
            if (nextPageNumber == 1) (params.loadSize / pageSize).coerceAtLeast(1) else 1
        val correctPageSize = pageSize * pagesToLoad

        val result = when (newsType) {
            is Everything -> {
                newsRepository.getEverything(
                    query = newsType.query,
                    page = nextPageNumber,
                    pageSize = correctPageSize,
                )
            }

            is TopHeadlines ->
                newsRepository.getTopHeadlines(
                    page = nextPageNumber,
                    pageSize = correctPageSize,
                )
        }

        result.fold(
            onSuccess = { newsPage ->
                val nextPage = nextPageNumber + pagesToLoad
                val loadedCount = (nextPage - 1) * pageSize
                val isLastPage = newsPage.articles.isEmpty() || loadedCount >= newsPage.totalResults

                return LoadResult.Page(
                    data = newsPage.articles,
                    prevKey = null, // Only paging forward
                    nextKey = if (isLastPage) null else nextPage
                )
            },
            onFailure = {
                return LoadResult.Error(it)
            }
        )
    }

    // Returning null makes a refresh load page 1 again.
    // Example: the first load has articles 1..60 and nextKey = 4. The user reads article 30 and refreshes.
    // With nextKey - 1, the refresh starts from page 3 (articles 41..60). prevKey is null,
    // so articles 1..40 can't load again, and article 30 disappears.
    // After a refresh, users also expect to see the newest articles at the top.
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? = null
}