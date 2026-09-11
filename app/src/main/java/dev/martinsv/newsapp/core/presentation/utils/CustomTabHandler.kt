package dev.martinsv.newsapp.core.presentation.utils

import android.content.Context
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

fun Context.openCustomTab(url: String) {
    val customTabsIntent = CustomTabsIntent.Builder().build()

    try {
        customTabsIntent.launchUrl(this, url.toUri())
    } catch (e: Exception) {
        //TODO use custom app logger
        Log.e("Custom tab", "Error in Custom tab launcher", e)
    }
}