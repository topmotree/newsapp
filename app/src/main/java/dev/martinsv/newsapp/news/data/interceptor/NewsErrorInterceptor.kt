package dev.martinsv.newsapp.news.data.interceptor

import dev.martinsv.newsapp.news.data.model.NewsErrorResponseDto
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

// NewsAPI sends errors with a non-2xx HTTP status. Retrofit only converts the body for
// 2xx responses, so it never reads the error body. This interceptor reads and parses the
// error body one time, here, so repository methods don't need to do it themselves.
@Singleton
class NewsErrorInterceptor @Inject constructor(
    private val json: Json,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (!response.isSuccessful) {
            val errorBody = response.peekBody(Long.MAX_VALUE).string()
            val errorDto = runCatching { json.decodeFromString<NewsErrorResponseDto>(errorBody) }
                .getOrNull()

            throw NewsApiException(
                code = errorDto?.code,
                message = errorDto?.message ?: response.message,
            )
        }
        return response
    }
}

// Must extend IOException. OkHttp only sends a thrown exception to the call's failure path
// when it is an IOException. Any other exception type crashes the dispatcher thread
class NewsApiException(
    val code: String?,
    message: String?
) : IOException(message)