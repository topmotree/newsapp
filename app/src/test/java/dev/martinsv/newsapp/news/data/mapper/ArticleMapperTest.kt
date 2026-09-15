package dev.martinsv.newsapp.news.data.mapper

import dev.martinsv.newsapp.news.data.model.ArticleDto
import dev.martinsv.newsapp.news.data.model.SourceDto
import junit.framework.TestCase.assertNull
import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleMapperTest {
    private val mapper = ArticleMapper()

    @Test
    fun `invalid publishedAt maps to null`() {
        val article = mapper.toDomain(
            ArticleDto(
                publishedAt = "something"
            )
        )

        assertNull(article.publishedAt)
    }

    @Test
    fun `nullable publishedAt maps to null`() {
        val article = mapper.toDomain(
            ArticleDto(
                publishedAt = null,
            )
        )

        assertNull(article.publishedAt)
    }

    @Test
    fun `correct iso date maps right`() {
        val publishedAt = "2026-09-15T10:52:42Z"
        val article = mapper.toDomain(
            ArticleDto(publishedAt = publishedAt)
        )

        assertEquals(publishedAt, article.publishedAt.toString())
    }


    @Test
    fun `all values maps correctly`() {
        val sourceDto = SourceDto(
            id = "1",
            name = "Source name"
        )
        val dto = ArticleDto(
            source = sourceDto,
            author = "Author",
            title = "Title",
            description = "Description",
            url = "some:url",
            urlToImage = "url:to:image",
            publishedAt = "2026-09-15T10:52:42Z",
            content = "Content"
        )

        val article = mapper.toDomain(dto)

        assertEquals(article.sourceName, sourceDto.name)
        assertEquals(article.author, dto.author)
        assertEquals(article.title, dto.title)
        assertEquals(article.description, dto.description)
        assertEquals(article.url, dto.url)
        assertEquals(article.urlToImage, dto.urlToImage)
        assertEquals(article.publishedAt.toString(), dto.publishedAt.toString())
        assertEquals(article.content, dto.content)
    }
}