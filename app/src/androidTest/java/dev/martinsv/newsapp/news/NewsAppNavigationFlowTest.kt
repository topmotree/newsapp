package dev.martinsv.newsapp.news

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.martinsv.newsapp.core.presentation.MainActivity
import dev.martinsv.newsapp.news.data.repository.createTestArticle
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NewsAppNavigationFlowTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeRule.waitUntil(5_000) {
            composeRule.onAllNodesWithText("Title 1").fetchSemanticsNodes().isNotEmpty()
        }
    }

    private val firstArticle = createTestArticle(1)

    @Test
    fun clickArticle_goBack_showsList() {
        composeRule.onNodeWithText(firstArticle.title!!).performClick()
        composeRule.onNodeWithText(firstArticle.content!!).assertIsDisplayed()

        Espresso.pressBack()

        composeRule.onNodeWithText(firstArticle.content).assertDoesNotExist()
        composeRule.onNodeWithText(firstArticle.title).assertIsDisplayed()
    }
}