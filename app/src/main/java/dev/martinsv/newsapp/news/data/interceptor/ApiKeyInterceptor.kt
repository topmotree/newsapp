package dev.martinsv.newsapp.news.data.interceptor

import dev.martinsv.newsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiKeyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("apiKey", BuildConfig.API_KEY)
            .build()
        return chain.proceed(request.newBuilder().url(url).build())
    }
}