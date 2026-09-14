package dev.martinsv.newsapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.martinsv.newsapp.core.presentation.navigation.AppNavHost
import dev.martinsv.newsapp.core.presentation.theme.NewsappTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsappTheme {
                val navController = rememberNavController()

                AppNavHost(navController)
            }
        }
    }
}