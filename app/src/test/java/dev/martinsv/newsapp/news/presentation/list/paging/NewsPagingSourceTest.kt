package dev.martinsv.newsapp.news.presentation.list.paging

import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import dev.martinsv.newsapp.news.data.repository.FakeNewsRepository
import dev.martinsv.newsapp.news.domain.DEFAULT_PAGE_SIZE
import junit.framework.TestCase.assertEquals
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
}