package dev.martinsv.newsapp.news.data

import dev.martinsv.newsapp.core.di.NetworkModule
import dev.martinsv.newsapp.core.utils.DispatcherProvider
import dev.martinsv.newsapp.core.utils.logger.TestLogger
import dev.martinsv.newsapp.news.data.interceptor.ApiKeyInterceptor
import dev.martinsv.newsapp.news.data.interceptor.NewsApiException
import dev.martinsv.newsapp.news.data.interceptor.NewsErrorInterceptor
import dev.martinsv.newsapp.news.data.mapper.ArticleMapper
import dev.martinsv.newsapp.news.data.mapper.NewsPageMapper
import dev.martinsv.newsapp.news.data.repository.RetrofitNewsRepository
import dev.martinsv.newsapp.news.di.NewsNetworkModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RetrofitNewsRepositoryTest {

    private val server = MockWebServer()
    private val dispatcher = UnconfinedTestDispatcher()
    private lateinit var repository: RetrofitNewsRepository

    @Before
    fun setUp() {
        server.start()

        // Same OkHttp and Retrofit setup as the app, only the base url is different
        val json = NetworkModule.provideJson()
        val client = NewsNetworkModule.provideNewsOkHttpClient(
            apiKeyInterceptor = ApiKeyInterceptor(),
            errorInterceptor = NewsErrorInterceptor(json),
            loggingInterceptor = HttpLoggingInterceptor(),
        )
        val retrofit = NewsNetworkModule.provideRetrofit(client, json)
            .newBuilder()
            .baseUrl(server.url("/"))
            .build()

        repository = RetrofitNewsRepository(
            newsApiService = NewsNetworkModule.provideNewsApiService(retrofit),
            newsPageMapper = NewsPageMapper(ArticleMapper()),
            dispatcher = DispatcherProvider(
                io = dispatcher,
                main = dispatcher,
                default = dispatcher
            ),
            logger = TestLogger,
        )
    }

    val errorBody = """
        {
        "status": "error",
        "code": "apiKeyMissing",
        "message": "Your API key is missing. Append this to the URL with the apiKey param, or use the x-api-key HTTP header."
        }
    """.trimIndent()

    @After
    fun tearDown() {
        server.close()
    }

    @Test
    fun `everything returns news page and sends correct request`() = runTest(dispatcher) {
        server.enqueue(MockResponse(body = readFile("everything_success.json")))

        val query = "test"
        val newsPage =
            repository.getEverything(query = query, page = 1, pageSize = 20).getOrThrow()

        assertEquals(3, newsPage.totalResults)
        assertEquals("Gizmodo.com", newsPage.articles.first().sourceName)

        val url = server.takeRequest().url
        assertEquals("/v2/everything", url.encodedPath)
        assertEquals(query, url.queryParameter("q"))
        assertEquals("1", url.queryParameter("page"))
        assertEquals("20", url.queryParameter("pageSize"))
        assertNotNull(url.queryParameter("apiKey"))
    }

    @Test
    fun `api error returns NewsApiException with error code`() = runTest(dispatcher) {
        server.enqueue(MockResponse(code = 401, body = errorBody))

        val error = repository.getTopHeadlines(page = 1).exceptionOrNull()

        assertTrue(error is NewsApiException)
        assertEquals("apiKeyMissing", (error as NewsApiException).code)
    }

    private fun readFile(name: String): String =
        javaClass.classLoader!!.getResource(name).readText()
}
