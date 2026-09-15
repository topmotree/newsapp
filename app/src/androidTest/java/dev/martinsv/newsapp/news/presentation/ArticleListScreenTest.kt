package dev.martinsv.newsapp.news.presentation

import androidx.compose.ui.test.junit4.v2.createEmptyComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.martinsv.newsapp.R
import dev.martinsv.newsapp.core.presentation.MainActivity
import dev.martinsv.newsapp.news.data.repository.FakeNewsRepository
import dev.martinsv.newsapp.news.data.repository.createTestArticle
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ArticleListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createEmptyComposeRule()

    @Inject
    lateinit var repository: FakeNewsRepository

    private var scenario: ActivityScenario<MainActivity>? = null
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val articleTitle = createTestArticle(1).title!!


    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        scenario?.close()
    }

    @Test
    fun loadingFailed_clickRetry_showArticles() {
        repository.error = IllegalStateException()

        val errorTitle = context.getString(R.string.article_list_refresh_error_title)
        launchActivity()
        waitAllNodesForText(errorTitle)
        composeRule.onNodeWithText(errorTitle).assertExists()

        repository.error = null

        val retryText = context.getString(R.string.global_retry_button_label)
        composeRule.onNodeWithText(retryText).performClick()

        waitAllNodesForText(articleTitle)
        composeRule.onNodeWithText(errorTitle).assertDoesNotExist()
    }

    @Test
    fun typeQuery_showsSearchResults() {
        val searchQuery = "query"

        launchActivity()
        waitAllNodesForText(articleTitle)

        val openSearchButtonLabel =
            context.getString(R.string.article_list_open_search_button_descritpion)
        composeRule.onNodeWithContentDescription(openSearchButtonLabel).performClick()

        val textfieldPlaceholder = context.getString(R.string.article_list_search_textfield_placeholder)
        composeRule.onNodeWithText(textfieldPlaceholder).performTextInput("query")

        waitAllNodesForText("$articleTitle $searchQuery")
        composeRule.onNodeWithText(articleTitle).assertDoesNotExist()
    }


    private fun waitAllNodesForText(articleTitle: String) {
        composeRule.waitUntil(5000) {
            composeRule.onAllNodesWithText(articleTitle).fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun launchActivity() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }
}