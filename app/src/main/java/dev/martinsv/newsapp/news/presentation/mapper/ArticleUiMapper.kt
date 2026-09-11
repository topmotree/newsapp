package dev.martinsv.newsapp.news.presentation.mapper

import dev.martinsv.newsapp.news.domain.Article
import dev.martinsv.newsapp.news.presentation.model.ArticleUiModel
import dev.martinsv.newsapp.news.presentation.utils.InstantFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleUiMapper @Inject constructor(private val formatter: InstantFormatter) {

    fun toUiModel(article: Article): ArticleUiModel {
        return ArticleUiModel(
            author = article.author,
            title = article.title,
            description = article.description,
            url = article.url,
            urlToImage = article.urlToImage,
            publishedAtFormatted = article.publishedAt?.let { formatter.format(it) },
            content = article.content,
        )
    }
}