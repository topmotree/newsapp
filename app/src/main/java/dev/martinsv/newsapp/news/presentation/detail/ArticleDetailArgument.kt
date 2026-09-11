package dev.martinsv.newsapp.news.presentation.detail

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import dev.martinsv.newsapp.news.presentation.model.ArticleUiModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

//TODO add comment why i decided to pass uiModel but not id or url.
@Serializable
data class ArticleDetailArgument(
    val article: ArticleUiModel,
) {
}

val articleDetailTypeMap = mapOf(typeOf<ArticleDetailArgument>() to ArticleDetailArgumentNavType)

// Navigation Compose only knows how to put primitives (String, Int, Boolean, enums) into a
// route. ArticleDetailArgument wraps ArticleUiModel, so it needs a custom NavType to encode it
// to a JSON string for the route and decode it back on the other side.

// Example: https://developer.android.com/guide/navigation/type-safe-destinations - Step 5
object ArticleDetailArgumentNavType : NavType<ArticleDetailArgument>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): ArticleDetailArgument? =
        bundle.getString(key)?.let { parseValue(it) }

    override fun parseValue(value: String): ArticleDetailArgument =
        Json.decodeFromString(Uri.decode(value))

    override fun serializeAsValue(value: ArticleDetailArgument): String =
        Uri.encode(Json.encodeToString(value))

    override fun put(bundle: Bundle, key: String, value: ArticleDetailArgument) {
        bundle.putString(key, serializeAsValue(value))
    }
}