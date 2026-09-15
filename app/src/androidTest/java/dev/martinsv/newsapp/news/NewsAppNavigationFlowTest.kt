package dev.martinsv.newsapp.news

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.test.espresso.Espresso
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.martinsv.newsapp.R
import dev.martinsv.newsapp.core.presentation.MainActivity
import dev.martinsv.newsapp.news.data.repository.createTestArticle
import org.junit.After
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
        Intents.init()
        composeRule.waitUntil(5_000) {
            composeRule.onAllNodesWithText("Title 1").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @After
    fun tearDown() {
        Intents.release()
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

    @Test
    fun readFullArticleClickOnDetail_opensBrowser() {
        intending(hasAction(Intent.ACTION_VIEW))
            .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))

        val readFullButtonText =
            composeRule.activity.getString(R.string.detail_read_full_article_button_label)

        composeRule.onNodeWithText(firstArticle.title!!).performClick()

        composeRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(hasText(readFullButtonText))

        composeRule.onNodeWithText(readFullButtonText).performClick()

        intended(hasAction(Intent.ACTION_VIEW))
        intended(hasData(firstArticle.url))
    }
}