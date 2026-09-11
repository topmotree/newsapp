package dev.martinsv.newsapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import dev.martinsv.newsapp.core.presentation.theme.NewsappTheme
import dev.martinsv.newsapp.news.presentation.list.ArticleListScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsappTheme {
                ArticleListScreen(
                    onArticleClick = {
                        //TODO implement
                    }
                )
            }
        }
    }
}