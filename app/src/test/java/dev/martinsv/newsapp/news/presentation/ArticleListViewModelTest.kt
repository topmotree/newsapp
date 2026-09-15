package dev.martinsv.newsapp.news.presentation

import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshots.Snapshot
import dev.martinsv.newsapp.news.data.repository.FakeNewsRepository
import dev.martinsv.newsapp.news.presentation.list.ArticleListEvent
import dev.martinsv.newsapp.news.presentation.list.ArticleListViewModel
import dev.martinsv.newsapp.news.presentation.list.paging.NewsType
import dev.martinsv.newsapp.news.presentation.mapper.ArticleUiMapper
import dev.martinsv.newsapp.news.presentation.model.ArticleUiModel
import dev.martinsv.newsapp.news.presentation.utils.InstantFormatter
import dev.martinsv.newsapp.news.utils.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArticleListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ArticleListViewModel

    @Before
    fun setUp() {
        viewModel = ArticleListViewModel(
            newsRepository = FakeNewsRepository(),
            articleUiMapper = ArticleUiMapper(InstantFormatter())
        )
    }

    @Test
    fun `changing query switches to everything news type`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.newsType.collect {} }

        setSearchText("test")
        assertEquals(NewsType.Everything("test"), viewModel.newsType.value)
    }

    @Test
    fun `clearing query switches to top headlines news type`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.newsType.collect {} }

        setSearchText("test")
        assertEquals(NewsType.Everything("test"), viewModel.newsType.value)

        setSearchText("")
        assertEquals(NewsType.TopHeadlines, viewModel.newsType.value)
    }

    @Test
    fun `search icon click shows search bar and clears search field state`() = runTest {
        setSearchText("qwerty")

        viewModel.onSearchIconClick()

        assertTrue(viewModel.isSearchBarVisible.value)
        assertEquals("", viewModel.searchFieldState.text.toString())
    }

    @Test
    fun `on article click emits correct event`() = runTest {
        val article = ArticleUiModel(
            sourceName = "The Verge",
            author = "Martins Vasiljevs",
            title = "Title short",
            description = "Description of the article.",
            url = "https://www.google.com/#q=tota",
            urlToImage = "https://thumb.wikimedia.org/wikipedia/commons/thumb/f/fa/Breil-Brigels._%28actm%29_02.jpg/1280px-Breil-Brigels._%28actm%29_02.jpg",
            publishedAtFormatted = "12 October 2023 12:12",
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere, ligula ut tincidunt bibendum, velit magna cursus massa, ullamcorper rhoncus erat ligula a lorem. Sed sodales nibh sit amet vulputate auctor. Integer metus augue, mollis vel scelerisque sit amet, egestas in est. Maecenas non mollis nulla. Nunc ut arcu tortor. Curabitur ornare dignissim augue. Quisque nec volutpat risus, sed consequat felis. Nulla elementum magna vitae imperdiet porta. Mauris velit quam, facilisis at purus ut, lobortis dapibus massa. Suspendisse potenti. Duis gravida dolor quis aliquet consequat. Fusce placerat dolor eu erat porta, vel vulputate diam venenatis. Donec id velit eget eros vulputate molestie. Cras ornare suscipit mauris, nec commodo neque."
        )

        viewModel.onArticleClick(article)
        assertEquals(ArticleListEvent.OpenArticle(article), viewModel.events.first())
    }

    private fun TestScope.setSearchText(value: String) {
        viewModel.searchFieldState.setTextAndPlaceCursorAtEnd(value)
        // On a device Compose sends this. In JVM tests snapshotFlow doesn't emit without it
        Snapshot.sendApplyNotifications()
        advanceUntilIdle()
    }
}