package dev.martinsv.newsapp.news.presentation.list.paging

import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import dev.martinsv.newsapp.news.data.repository.FakeNewsRepository
import dev.martinsv.newsapp.news.domain.DEFAULT_PAGE_SIZE
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NewsPagingSourceTest {

    @Test
    fun `next page size is correct after initial load`() = runTest {
        val pager = TestPager(
            defaultPagingConfig,
            NewsPagingSource(FakeNewsRepository(), NewsType.TopHeadlines)
        )

        val firstPage = pager.refresh() as PagingSource.LoadResult.Page
        val secondPage = pager.append() as PagingSource.LoadResult.Page

        assertEquals(defaultPagingLoadSize, firstPage.data.size)
        assertEquals(DEFAULT_PAGE_SIZE, secondPage.data.size)
    }

    @Test
    fun `last page has no next key`() = runTest {
        val articlesCount = 70
        val repository = FakeNewsRepository(articlesCount)
        val pagingSource = NewsPagingSource(repository, NewsType.TopHeadlines)
        val pager = TestPager(defaultPagingConfig, pagingSource)

        pager.refresh()
        val nextPage = pager.append() as PagingSource.LoadResult.Page

        assertEquals(articlesCount - defaultPagingLoadSize, nextPage.data.size)
        assertNull(nextPage.nextKey)
    }

    @Test
    fun `no next page if first page has all articles`() = runTest {
        val articlesCount = defaultPagingLoadSize
        val repository = FakeNewsRepository(articlesCount)
        val pager =
            TestPager(defaultPagingConfig, NewsPagingSource(repository, NewsType.TopHeadlines))

        val firstPage = pager.refresh() as PagingSource.LoadResult.Page

        assertEquals(defaultPagingLoadSize, firstPage.data.size)
        assertNull(firstPage.nextKey)
    }

    @Test
    fun `paging uses repository error`() = runTest {
        val error = IllegalArgumentException()
        val repository = FakeNewsRepository(error = error)
        val pagingSource = NewsPagingSource(repository, NewsType.TopHeadlines)
        val pager = TestPager(defaultPagingConfig, pagingSource)

        val refreshResult = pager.refresh()
        assert(refreshResult is PagingSource.LoadResult.Error)

        val errorResult = refreshResult as PagingSource.LoadResult.Error
        assertEquals(error, errorResult.throwable)
    }
}