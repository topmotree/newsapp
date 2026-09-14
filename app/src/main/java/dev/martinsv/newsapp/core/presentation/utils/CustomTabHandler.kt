package dev.martinsv.newsapp.core.presentation.utils

import android.content.Context
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import dev.martinsv.newsapp.core.utils.logger.logger

fun Context.openCustomTab(url: String) {
    val customTabsIntent = CustomTabsIntent.Builder().build()

    try {
        customTabsIntent.launchUrl(this, url.toUri())
    } catch (e: Exception) {
        logger.e(error = e) { "Error in Custom tab launcher" }
    }
}