package dev.martinsv.newsapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.martinsv.newsapp.news.presentation.detail.ArticleDetailArgument
import dev.martinsv.newsapp.news.presentation.detail.ArticleDetailScreen
import dev.martinsv.newsapp.news.presentation.detail.articleDetailTypeMap
import dev.martinsv.newsapp.news.presentation.list.ArticleListScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = ArticleListRoute
    ) {

        composable<ArticleListRoute> {
            ArticleListScreen(
                openArticleDetail = {
                    navController.navigate(
                        ArticleDetailRoute(ArticleDetailArgument(it))
                    )
                }
            )
        }

        composable<ArticleDetailRoute>(typeMap = articleDetailTypeMap) {
            ArticleDetailScreen()
        }
    }
}