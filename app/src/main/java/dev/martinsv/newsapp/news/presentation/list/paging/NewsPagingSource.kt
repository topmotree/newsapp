package dev.martinsv.newsapp.news.presentation.list.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.martinsv.newsapp.news.domain.Article
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
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val nextPageNumber = params.key ?: 1
        val pageSize = params.loadSize

        val result = when (newsType) {
            is Everything -> {
                newsRepository.getEverything(
                    query = newsType.query,
                    page = nextPageNumber,
                    pageSize = pageSize,
                )
            }

            is TopHeadlines ->
                newsRepository.getTopHeadlines(
                    page = nextPageNumber,
                    pageSize = pageSize,
                )
        }

        result.fold(
            onSuccess = { newsPage ->
                val isLastPage = nextPageNumber * pageSize >= newsPage.totalResults

                return LoadResult.Page(
                    data = newsPage.articles,
                    prevKey = null, // Only paging forward
                    nextKey = if (isLastPage) null else nextPageNumber + 1
                )
            },
            onFailure = {
                return LoadResult.Error(it)
            }
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.nextKey?.minus(1)
        }
    }
}